package com.xishatou;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xishatou.tcp.Client;
import com.xishatou.tcp.Cons;
import com.xishatou.utils.API;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Wifi_Activity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connect();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    String ip;
    String port;

    private void initView() {

        ip = "106.39.79.26";
        port = "7005";
    }


    // 取消注册
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (client != null) {
            client.gb();
        } else {

        }

        Cons.isConnecting = true;

    }

    Client client;

    private void send() {
        try {
//            String stt = JX_Utils.stringToAsciiToHexString("7E0000000000000000000000007E");
//            byte[] bytes1 = API.split_joint("000000000000000000000000");
            byte[] bytes = HexUtil.hexToBytes("7E0000000000000000000000007E", false);
            Log.e("Android",""+ bytes);
            client.fasong(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        1542348443650
//        1542348443938

    }

    /**
     * 连接服务器
     */
    public void connect() {
        if (Cons.isConnecting) {


            if (NetUtil.isNetworkAvailable(Wifi_Activity.this)) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        client = new Client(ip, Integer.valueOf(port), h);
                        return null;
                    }
                }.execute();

            } else {
                Toast.makeText(Wifi_Activity.this, "请检查网络!", Toast.LENGTH_SHORT).show();
            }


        } else {
            if (client != null) {
                client.gb();
                Cons.isConnecting = true;
            } else {

            }


        }
    }


    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case 1:
//                    Intent intent = new Intent(Zhanshi_Activity.this, MyService.class);
//                    startService(intent);
//                    break;
                case 2:
                    Log.e("Android", "连接失败！");


                     Toast.makeText(Wifi_Activity.this, "连接失败！！!", Toast.LENGTH_SHORT).show();
                    Cons.isConnecting = true;
                    break;
                case 3:
                    Toast.makeText(Wifi_Activity.this, "断开连接！", Toast.LENGTH_SHORT).show();
                    Log.e("Android", "断开连接！");


                    Cons.isConnecting = true;

                    break;
                case 4:
                    Toast.makeText(Wifi_Activity.this, "设备连接成功！", Toast.LENGTH_SHORT).show();
                    Log.e("Android", "服务器连接成功！");


                    Cons.isConnecting = false;
//                    send();

                    break;
                case 5:
                    String cc = (String) msg.obj;
                    Log.e("Android", "接收内容:"+cc+"，时间戳"+System.currentTimeMillis() );
//                    if (cc.contains("ABF")) {
//                        String ID = cc.substring(cc.indexOf("ABF") + 3, cc.length());
//                        Log.e("id", ID);
//
//
//                    }
                    Toast.makeText(Wifi_Activity.this, "设置成功", Toast.LENGTH_SHORT).show();
//


                    break;
                default:
                    break;
            }

        }
    };

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                connect();
                break;
            case R.id.button2:
                send();
                Log.e("Android", "发送时间戳:"+System.currentTimeMillis() );
                break;
        }
    }



}