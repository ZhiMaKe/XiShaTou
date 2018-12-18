package com.xishatou;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/11/16.
 */

public class JX_Utils {

    public static String stringToAsciiToHexString(String strAsci) throws Exception {
        final byte[] bytes = hexStringToBytes(strAsci);
        System.out.println(bytes.length + ",bytes=" + Arrays.toString(bytes));
        String strResult = HexToString(bytes);
        return strResult;
    }

    public static byte[] hexStringToBytes(String hexString) throws Exception {
        int len = hexString.length() / 2;
        byte[] b = new byte[len];

        for (int i = 0; i < len; ++i) {
            b[i] = (byte) Integer.valueOf(hexString.substring(i * 2, i * 2 + 2), 16).intValue();
        }
        return b;
    }

    public static String HexToString(byte[] inHex) {
        byte i;
        String strResult = "";
        for (i = 0; i < inHex.length; i++) {
            strResult += String.valueOf(DIGITS_UPPER[(0xF0 & inHex[i]) >>> 4]) + String.valueOf(DIGITS_UPPER[inHex[i] & 0x0F]);
            //System.out.println(i+",resultStr="+strResult);
        }
        return strResult;
    }
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
}
