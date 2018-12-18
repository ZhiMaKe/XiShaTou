package com.xishatou.bean;

/**
 * Created by Administrator on 2018/12/5.
 */

public class Head {
    //消息ID(2字节,4位)
    String ID;
    //属性(2字节,4位)
    String ShuXing;
    //标识(6字节,12位)
    String  BiaoShi;
    //流水号(1字节,2位)
    String  LiuShui;

    public String putHead(String ID, String shuXing, String biaoShi, String liuShui) {
        this.ID = ID;
        ShuXing = shuXing;
        BiaoShi = biaoShi;
        LiuShui = liuShui;
        return  ID+ShuXing+BiaoShi+LiuShui;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getShuXing() {
        return ShuXing;
    }

    public void setShuXing(String shuXing) {
        ShuXing = shuXing;
    }

    public String getBiaoShi() {
        return BiaoShi;
    }

    public void setBiaoShi(String biaoShi) {
        BiaoShi = biaoShi;
    }

    public String getLiuShui() {
        return LiuShui;
    }

    public void setLiuShui(String liuShui) {
        LiuShui = liuShui;
    }
}
