package com.xishatou.utils;

public class BytesUtils {
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	/**  
	 * 把字节数组转换成16进制字符串  
	 * @param bArray  
	 * @return  
	 */   
	public static String bytesToHexString(byte[] bArray) {   
		StringBuffer sb = new StringBuffer(bArray.length);   
		String sTemp;   
		for (int i = 0; i < bArray.length; i++) {   
			sTemp = Integer.toHexString(0xFF & bArray[i]);   
			if (sTemp.length() < 2)   
				sb.append(0);   
			sb.append(sTemp.toUpperCase());   
		}   
		return sb.toString();   
	}  

	/**
	 * 将字符串转换成byte数组
	 * 
	 * @param msg
	 * @return
	 */
	public static byte[] stringToHex(String msg) {
		byte b[] = new byte[0];
		if (!msg.equals("") && msg != null) {
			b = AppendBCDString(b, msg);
			return b;
		}
		return null;
	}
	/**
	 * byte数组追加BCD码
	 * @param bytes 目标数组
	 * @param value BCD字符串
	 * @return 追加BCD码后的数组
	 */
	public static byte[] AppendBCDString(byte[] bytes, String value) {
		byte[] abts = new byte[value.length() / 2];
		byte[] bbts = new byte[value.length() / 2 + bytes.length];
		abts = Str2BCD(value);
		bbts = AppendBytes(bytes, abts);
		return bbts;
	}

	/**
	 * 字符串转换BCD码
	 * @param value 字符串
	 * @return BCD码的byte数组
	 */
	public static byte[] Str2BCD(String value) {
		int iLen = value.length();
		int iMod = iLen % 2;
		if ((iLen == 0) || (iMod != 0)) {
			return new byte[0];
		}
		if (iMod != 0) {
			value = "0" + value;
			iLen = value.length();
		}
		byte abts[] = new byte[iLen];
		if (iLen >= 2) {
			iLen = iLen / 2;
		}
		byte bbts[] = new byte[iLen];
		abts = value.getBytes();
		int j, k;
		for (int p = 0; p < value.length() / 2; p++) {
			if ((abts[2 * p] >= '0') && (abts[2 * p] <= '9')) {
				j = abts[2 * p] - '0';
			} else if ((abts[2 * p] >= 'a') && (abts[2 * p] <= 'z')) {
				j = abts[2 * p] - 'a' + 0x0a;
			} else {
				j = abts[2 * p] - 'A' + 0x0a;
			}
			if ((abts[2 * p + 1] >= '0') && (abts[2 * p + 1] <= '9')) {
				k = abts[2 * p + 1] - '0';
			} else if ((abts[2 * p + 1] >= 'a') && (abts[2 * p + 1] <= 'z')) {
				k = abts[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abts[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbts[p] = b;
		}
		return bbts;
	}

	/**
	 * byte数组追加byte数组
	 * @param bytes 目标数组
	 * @param append 追加的数组
	 * @return 追加数组后的数组
	 */
	public static byte[] AppendBytes(byte[] bytes, byte[] append) {
		return AppendBytes(bytes, append, append.length);
	}

	/**
	 * byte数组追加byte数组
	 * @param bytes 目标数组
	 * @param append 追加的数组
	 * @param len 追加的字节数
	 * @return 追加数组后的数组
	 */
	public static byte[] AppendBytes(byte[] bytes, byte[] append, int len) {
		byte[] bbts = new byte[bytes.length + append.length];
		System.arraycopy(bytes, 0, bbts, 0, bytes.length);
		System.arraycopy(append, 0, bbts, bytes.length, len);
		return bbts;
	}
	
	/**
	 * byte数组转换字符串(默认各字节之间空格分隔,并且是全数组转换)
	 * 
	 * @param bytes
	 *            目标数组
	 * @return 转换后的结果
	 */
	public static String BytesToHex(byte[] bytes) {
		return BytesToHex(bytes, " ");
	}
	
	/**
	 * byte数组转换字符串(默认全数组转换)
	 * 
	 * @param bytes
	 *            目标数组
	 * @param delimiter
	 *            各个字节之间的分隔符
	 * @return 转换后的结果
	 */
	public static String BytesToHex(byte[] bytes, String delimiter) {
		return BytesToHex(bytes, 0, bytes.length - 1, delimiter);
	}
	
	/**
	 * 数组转换字符串
	 * 
	 * @param bytes
	 *            目标数组
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @param delimiter
	 *            各个字节之间的分隔符
	 * @return 转换后的结果
	 */
	public static String BytesToHex(byte[] bytes, int start, int end,
			String delimiter) {
		String value = "";
		if (start > end) {
			System.exit(0);
		}
		if (bytes.length - 1 < end) {
			end = bytes.length - 1;
		}
		for (int i = start; i <= end; i++) {
			value += String.format("%02x", bytes[i]).toUpperCase() + delimiter;
		}
		return RightTrim(value);
	}
	
	/**
	 * 去除字符串右侧空格
	 * 
	 * @param value
	 *            目标字符串
	 * @return 去除空格后的字符串
	 */
	public static String RightTrim(String value) {
		if (value == null || value.trim().length() == 0)
			return null;
		if (value.trim().length() == value.length())
			return value;
		if (!value.startsWith(" ")) {
			return value.trim();
		} else {
			return value.substring(0,
					value.indexOf(value.trim().substring(0, 1))
							+ value.trim().length());
		}
	}


	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		for (int i=begin;i<begin+count; i++) bs[i-begin] = src[i];
		return bs;
	}
}
