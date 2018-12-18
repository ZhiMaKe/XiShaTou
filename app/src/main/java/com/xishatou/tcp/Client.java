package com.xishatou.tcp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;

import java.net.InetSocketAddress;

/**
 * Created by reweber on 12/20/14.
 */
public class Client {

    private String host;
    private int port;
    AsyncSocket socket2;
    Handler handler;
    public Client(String host, int port, Handler handler) {
        this.host = host;
        this.port = port;
        this.handler = handler;
        setup();
    }

    private void setup() {
        AsyncServer.getDefault().connectSocket(new InetSocketAddress(host, port), new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                try {
                    handleConnectCompleted(ex, socket);
                    Log.e("Android", "连接成功");
                    socket2 = socket;

                    Message message =new Message();
                    message.what=4;
                    handler.sendMessage(message);
                }catch (Exception e){
                    Message message =new Message();
                    message.what=2;
                    handler.sendMessage(message);
                    Log.e("Android", "连接失败！！！！！");
                }

            }
        });
    }

    public void gb() {
        if (socket2!=null) {
            socket2.end();
            socket2.close();
        }

    }

    public void fasong(byte[] bytes  ) {
        if (socket2!=null ) {
            Util.writeAll(socket2, bytes, new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    if (ex != null) throw new RuntimeException(ex);
                    Log.e("Android", "发送成功");
                }
            });
        } else {

        }

    }

    private void handleConnectCompleted(Exception ex, final AsyncSocket socket) {
        if (ex != null) throw new RuntimeException(ex);


        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                Message message =new Message();
                message.what=5;
                message.obj=new String(bb.getAllByteArray());
                handler.sendMessage(message);
                //收到消息
                Log.e("Android", "收到消息"+message.obj);

            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) throw new RuntimeException(ex);
                //服务器断开连接  断网了
                Log.e("Android", "断开连接");
                Message message =new Message();
                message.what=3;
                handler.sendMessage(message);
                //断网了3
                System.out.println("[Client] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                try {
                    Log.e("Android", "关闭");
                    System.out.println("关闭");   //服务器关闭了 断网了1

                    if (ex != null) throw new RuntimeException(ex);
                } catch (Exception e) {
                    Log.e("Android", "关闭异常");            //断网了2
                    System.out.println("关闭2");
                    Message message =new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    socket.end();
                   socket.close();

                }


            }
        });
    }
}
