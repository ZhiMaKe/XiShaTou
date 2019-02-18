package com.xishatou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xishatou.socket.UDPSocket;


/**
 * Created by melo on 2017/9/20.
 */

public class UDPSocketActivity extends AppCompatActivity implements Objectcallback {
    @Override
    public void getmessages(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    String SS = "7E801200141001A00000010001000001020A030703397E";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);


        //   SS.substring(2,6);
//        Log.e("7878",  SS.substring(2,6));
//        Log.e("7878", SS.substring(SS.length() - 14, SS.length() - 4));
        String honglvdeng = SS.substring(SS.length() - 14, SS.length() - 4);

        String s = JX_Utils.hexString2binaryString( honglvdeng.substring(0,2));
        Log.e("7878",  s );


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


}
