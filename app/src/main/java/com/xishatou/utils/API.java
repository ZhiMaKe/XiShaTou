package com.xishatou.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/11/26.
 */

public class API {
    private static final String TAG = API.class.getName();

    /**
     * 拼接好的协议，直接下发
     * @param msg
     * @return
     */
    public static  byte[] split_joint(String msg){


        byte[] data=   CodeTool.HexToByteArr(CodeTool
                .StringToHexString(msg));
        final byte[] tempData = data;
        byte[] tempData2 = CodeTool.HexToByteArr("7E"
                + CodeTool.getEnEscape7EString(CodeTool
                .ByteArrToHexArr(tempData)) + "7E");
        Log.e(TAG, "数据"+"7E"
                + CodeTool.getEnEscape7EString(CodeTool
                .ByteArrToHexArr(tempData)) + "7E");
        return  tempData2;
    }
    public static String split_joint2(String msg){


        byte[] data=   CodeTool.HexToByteArr(CodeTool
                .StringToHexString(msg));
        final byte[] tempData = data;
        byte[] tempData2 = CodeTool.HexToByteArr("7E"
                + CodeTool.getEnEscape7EString(CodeTool
                .ByteArrToHexArr(tempData)) + "7E");
        Log.e(TAG, "数据"+"7E"
                + CodeTool.getEnEscape7EString(CodeTool
                .ByteArrToHexArr(tempData)) + "7E");
        return  "7E"
                + CodeTool.getEnEscape7EString(CodeTool
                .ByteArrToHexArr(tempData)) + "7E";
    }
    /**
     * 7e7e协议字段解析
     */
    public static  byte[] fasong(String msg){
        byte[] data= new byte[0];
        byte[] bytes = BytesUtils.AppendBCDString(data, msg);
        return  bytes;

    }


    public static int returnActualLength(byte[] data) {
        int i = 0;
        for (; i < data.length; i++) {
            if (data[i] == '\0')
                break;
        }
        return i;
    }

    public static long getUint32(long l){
        return l & 0x00000000ffffffff;
    }
}
