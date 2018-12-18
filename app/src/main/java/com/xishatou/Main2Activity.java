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
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.xishatou.R;
import com.xishatou.View.DashboardView4;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
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
    }

    private boolean isAnimFinished = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dashboard_view_4:
                if (isAnimFinished) {
                    @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animator = ObjectAnimator.ofInt(mDashboardView4, "mRealTimeValue",
                            mDashboardView4.getVelocity(), new Random().nextInt(180));
                    animator.setDuration(1500).setInterpolator(new LinearInterpolator());
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

    public String getriqi( ) {
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
                    time.setText(  sysTimeStr); //更新时间

                    break;

                default:
                    break;

            }
        }
    };
}
