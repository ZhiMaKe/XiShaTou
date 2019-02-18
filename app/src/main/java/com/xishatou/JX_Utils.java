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



    /**
     * 计价器   (UINT8) 16进制数据转2进制BYTE每位
     * 将16进制字符串转化成二进制字符串
     *
     * @param hexString 16进制字符串
     * @return
     */
    public static String hexString2binaryString(String hexString)

    {

        if (hexString == null || hexString.length() % 2 != 0)

            return null;

        String bString = "", tmp;

        for (int i = 0; i < hexString.length(); i++)

        {

            tmp = "0000"

                    + Integer.toBinaryString(Integer.parseInt(hexString

                    .substring(i, i + 1), 16));

            bString += tmp.substring(tmp.length() - 4);

        }

        return bString;

    }

    /**
     * 计价器  16进制byte    转Stringz
     * 16进制的Byte字符串转String
     *
     * @param s 16进制字符串
     */

    public static String HexToString(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
}
