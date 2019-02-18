package com.xishatou;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.xishatou.View.DashboardView4;
import com.xishatou.socket.UDPSocket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, Objectcallback {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.wendu)
    TextView wendu;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.Data)
    TextView Data;
    @BindView(R.id.week)
    TextView week;
    @BindView(R.id.dashboard_view_4)
    DashboardView4 mDashboardView4;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.textView76)
    TextView textView76;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.textView77)
    TextView textView77;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.textView56)
    TextView textView56;
    @BindView(R.id.im_zuo)
    ImageView imZuo;
    @BindView(R.id.im_zhong)
    ImageView imZhong;
    @BindView(R.id.im_you)
    ImageView imYou;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.textView6)
    TextView textView6;
    UDPSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mDashboardView4.setOnClickListener(this);
        String getweek = getweek();
        week.setText(getweek);
        Data.setText(getriqi());
        new TimeThread().start(); //启动新的线程
        System.currentTimeMillis();
        System.currentTimeMillis();
        System.currentTimeMillis();
        setColor2();
        socket = new UDPSocket(this);
        socket.startUDPSocket();
    }

    public void setColor1() {
        imZuo.setImageResource(R.drawable.zuo_hong);
        imZhong.setImageResource(R.drawable.zhong_lv);
        imYou.setImageResource(R.drawable.zuo_hong);
    }

    public void setColor2() {
        imZuo.setImageResource(R.drawable.you_huang);
        imZhong.setImageResource(R.drawable.you_huang);
        imYou.setImageResource(R.drawable.you_huang);
    }

    public void setColor3() {
        imZuo.setImageResource(R.drawable.zhong_lv);
        imZhong.setImageResource(R.drawable.zuo_hong);
        imYou.setImageResource(R.drawable.zhong_lv);
    }

    private boolean isAnimFinished = true;
    int i = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dashboard_view_4:
                if (isAnimFinished) {
                    i++;
                    @SuppressLint("ObjectAnimatorBinding")
                    ObjectAnimator animator = ObjectAnimator.ofInt(mDashboardView4, "mRealTimeValue",
                            mDashboardView4.getVelocity(), i);
                    animator.setDuration(500).setInterpolator(new LinearInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimFinished = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimFinished = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isAnimFinished = true;
                        }
                    });
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDashboardView4.setVelocity(value);
                        }
                    });
                    animator.start();
                }

                break;
        }
    }


    public String getweek() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String currSun = dateFm.format(date);
        System.out.println(currSun);
        return currSun;

    }

    public String getriqi() {
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH) + 1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day;

    }

    String position = "1";
    int hongtimes;
    int huangtimes;
    int lvtimes;

    @Override
    public void getmessages(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("111", "xx：：" + message);

                if (message.substring(2, 6).equals("8012")) {
                    String honglvdeng = message.substring(message.length() - 14, message.length() - 4);
                    String s = JX_Utils.hexString2binaryString(honglvdeng.substring(0, 2));
                    if (s.equals("00000001")) {
                        String lvtime = honglvdeng.substring(2, 4);
                        String s1 = JX_Utils.HexToString(lvtime);
                        lvtimes = Integer.parseInt(s1);
                        Log.e("777", "绿" + s1);
                        String dangqiantime = honglvdeng.substring(8, 10);
                        lvtimes = Integer.parseInt(dangqiantime);
                        position = "0";
                        setColor1();
                    } else if (s.equals("00000010")) {
                        String huangtime = honglvdeng.substring(4, 6);
                        String s1 = JX_Utils.HexToString(huangtime);
                        huangtimes = Integer.parseInt(s1);
                        String dangqiantime = honglvdeng.substring(8, 10);
                        Log.e("777", "黄" + s1);
                        position = "1";
                        huangtimes = Integer.parseInt(dangqiantime);
                        setColor2();
                    } else if (s.equals("00000100")) {
                        String hongtime = honglvdeng.substring(6, 8);
                        String s1 = JX_Utils.HexToString(hongtime);
                        hongtimes = Integer.parseInt(s1);
                        Log.e("777", "红" + s1);
                        String dangqiantime = honglvdeng.substring(8, 10);
                        hongtimes = Integer.parseInt(dangqiantime);
                        position = "2";
                        setColor3();
                    }

                } else {

                }

//


            }
        });
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }


    //在主线程里面处理消息并更新UI界面
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd hh:mm:ss", sysTime);//时间显示格式
                    time.setText(sysTimeStr); //更新时间
                    if (position.equals("0")) {
                        lvtimes--;
                        if (lvtimes == 0) {
                            setColor2();
                            lvtimes = 0;
                        }
                    } else if (position.equals("1")) {
                        huangtimes--;
                        if (huangtimes == 0) {
                            setColor2();
                            huangtimes = 0;
                        }
                    } else if (position.equals("2")) {
                        hongtimes--;
                        if (hongtimes == 0) {
                            setColor2();
                            hongtimes = 0;
                        }

                    }
                    break;

                default:
                    break;

            }
        }
    };
}
