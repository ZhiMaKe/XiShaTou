package com.xishatou.socket;

import android.content.Context;
import android.util.Log;

import com.xishatou.JX_Utils;
import com.xishatou.bean.Head;
import com.xishatou.bean.Heart;
import com.xishatou.bean.Users;
import com.xishatou.utils.API;
import com.xishatou.utils.BytesUtils;
import com.xishatou.utils.GetCharAscii;
import com.xishatou.utils.Resert;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




/**
 * Created by melo on 2017/9/20.
 */

public class UDPSocket {

    private static final String TAG = "zzzzzzzzzzzzzzzzz";

    // 单个CPU线程池大小
    private static final int POOL_SIZE = 5;

    private static final int BUFFER_LENGTH = 1024;
    private byte[] receiveByte = new byte[BUFFER_LENGTH];

    private static final String BROADCAST_IP = "192.168.10.79";

    // 端口号，飞鸽协议默认端口2425
    public static final int CLIENT_PORT = 4147;

    private boolean isThreadRunning = false;

    private Context mContext;
    private DatagramSocket client;
    private DatagramPacket receivePacket;

    private long lastReceiveTime = 0;
    private static final long TIME_OUT = 120 * 1000;
    private static final long HEARTBEAT_MESSAGE_DURATION = 10 * 1000;

    private ExecutorService mThreadPool;
    private Thread clientThread;
    private HeartbeatTimer timer;
    private Users localUser;
    private Users remoteUser;

    public UDPSocket(Context context) {

        this.mContext = context;

        int cpuNumbers = Runtime.getRuntime().availableProcessors();
        // 根据CPU数目初始化线程池
        mThreadPool = Executors.newFixedThreadPool(cpuNumbers * POOL_SIZE);
        // 记录创建对象时的时间
        lastReceiveTime = System.currentTimeMillis();

//        createUser();
    }

    /**
     * 创建本地用户信息
     */
//    private void createUser() {
//        if (localUser == null) {
//            localUser = new Users();
//        }
//        if (remoteUser == null) {
//            remoteUser = new Users();
//        }
//
//        localUser.setImei(DeviceUtil.getDeviceId(mContext));
//        localUser.setSoftVersion(DeviceUtil.getPackageVersionCode(mContext));
//
//        if (WifiUtil.getInstance(mContext).isWifiApEnabled()) {// 判断当前是否是开启热点方
//            localUser.setIp("192.168.43.1");
//        } else {// 当前是开启 wifi 方
//            localUser.setIp(WifiUtil.getInstance(mContext).getLocalIPAddress());
//            remoteUser.setIp(WifiUtil.getInstance(mContext).getServerIPAddress());
//        }
//    }


    public void startUDPSocket() {
        if (client != null) return;
        try {
            // 表明这个 Socket 在设置的端口上监听数据。
            client = new DatagramSocket(CLIENT_PORT);

            if (receivePacket == null) {
                // 创建接受数据的 packet
                receivePacket = new DatagramPacket(receiveByte, BUFFER_LENGTH);
            }

            startSocketThread();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启发送数据的线程
     */
    private void startSocketThread() {
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "clientThread is running...");
                receiveMessage();
            }
        });
        isThreadRunning = true;
        clientThread.start();

        startHeartbeatTimer();
    }

    /**
     * 处理接受到的消息
     */
    private void receiveMessage() {
        while (isThreadRunning) {
            try {
                if (client != null) {
                    client.receive(receivePacket);
                }
                lastReceiveTime = System.currentTimeMillis();
                Log.e(TAG, "receive packet success...");
            } catch (IOException e) {
                Log.e(TAG, "UDP数据包接收失败！线程停止");
                stopUDPSocket();
                e.printStackTrace();
                return;
            }

            if (receivePacket == null || receivePacket.getLength() == 0) {
                Log.e(TAG, "无法接收UDP数据或者接收到的UDP数据为空");
                continue;
            }
            byte[] data = receivePacket.getData();
            int i = API.returnActualLength(data);
             byte[] bytes = BytesUtils.subBytes(data, 0, i);
            String s = BytesUtils.Bytes2HexString(bytes);





            Log.e(TAG, s + " ,from: " + receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort());

            //解析接收到的 json 信息

            // 每次接收完UDP数据后，重置长度。否则可能会导致下次收到数据包被截断。
            if (receivePacket != null) {
                receivePacket.setLength(BUFFER_LENGTH);
            }
        }
    }

    public void stopUDPSocket() {
        isThreadRunning = false;
        receivePacket = null;
        if (clientThread != null) {
            clientThread.interrupt();
        }
        if (client != null) {
            client.close();
            client = null;
        }
        if (timer != null) {
            timer.exit();
        }
    }

    /**
     * 启动心跳，timer 间隔十秒
     */
    private void startHeartbeatTimer() {
        timer = new HeartbeatTimer();
        timer.setOnScheduleListener(new HeartbeatTimer.OnScheduleListener() {
            @Override
            public void onSchedule() {
                Log.e(TAG, "timer is onSchedule...");
                long duration = System.currentTimeMillis() - lastReceiveTime;
                Log.e(TAG, "duration:" + duration);
                if (duration > TIME_OUT) {//若超过两分钟都没收到我的心跳包，则认为对方不在线。
                    Log.d(TAG, "超时，对方已经下线");
                    // 刷新时间，重新进入下一个心跳周期
                    lastReceiveTime = System.currentTimeMillis();
                } else if (duration > HEARTBEAT_MESSAGE_DURATION) {//若超过十秒他没收到我的心跳包，则重新发一个。
                    String string = "hello,this is a heartbeat message";
                    sendMessage(string);
                }
            }

        });
        timer.startTimer(0, 1000 * 10);
    }
    Heart heart ;
    Head head ;
    /**
     * 发送心跳包
     *
     * @param message
     */
    public void sendMessage(final String message) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress targetAddress = InetAddress.getByName(BROADCAST_IP);

                    if (heart==null) {
                        heart=new  Heart();
                    }

                    if (head==null) {
                        head=new  Head();
                    }
                    String s5 = GetCharAscii.wordToAsciiGB2312("京A12345");
                    String Plate_number = GetCharAscii.padLeft(s5, 20, '0');
                    String JD =  String.format("%08X",(long)114.28 * 10000);
                    String WD = String.format("%08X",(long)24.28 * 10000);
                    String heartBody = heart.PutData("0002", "0003", Plate_number,
                            JD, WD, "0002", "01", "00",
                            "01", "01", "01", "000000000006");


                    String hex = Integer.toHexString(heartBody.length()/2);
                    Log.e(TAG, "心跳长度"+heartBody.length()/2);
                    if (hex.length()==2) {
                        hex="00"+hex;
                    }


                    //long JD10 = API.getUint32(JD);
                   // long WD10 = API.getUint32(WD);
                    //String s1 = String.valueOf(JD10);
                    //String s2 = String.valueOf(WD10);
//                    String s3 = JX_Utils.stringToAsciiToHexString(s1);
//                    String s4 =   JX_Utils.stringToAsciiToHexString(s2);


                    String headbody = head.putHead("0001", hex, "666666666666", "0002");


                    String s = Resert.sendData(headbody, heartBody);
                    Log.e(TAG, "心跳"+s);
                    final byte[] bytes = API.fasong( s);


                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length, targetAddress, CLIENT_PORT);

                    client.send(packet);

                    // 数据发送事件
                    Log.e(TAG, "数据发送成功");

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
