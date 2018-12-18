package com.xishatou;

/**
 * Hex 工具类
 * <pre>
 * 功能:
 * 1. byte 数组转换为 Hex 字符串: {@link HexUtil#hashCode()}
 * 2. Hex 字符串转换为 byte 数组: <b>toBytes</b><br>
 * </pre>
 * @author Chia
 * @date 2017-09-20
 * @version 1.0
 */
public class HexUtil {

	// 默认分隔符
	private static final char DEFAULT_SEPARATOR = '\u0020';
	// 小写转换表
	private static final char[] HEX_CHAR_LOWER =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	// 大写转换表
	private static final char[] HEX_CHAR_UPPER =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static String bytesToHex(final byte[] data) {
		return bytesToHex(data, data.length, true, DEFAULT_SEPARATOR);
	}

	public static String bytesToHex(final byte[] data, final int len) {
		return bytesToHex(data, len, true, DEFAULT_SEPARATOR);
	}

	public static String bytesToHex(final byte[] data, final char separator) {
		return bytesToHex(data, data.length, true, separator);
	}

	public static String bytesToHex(final byte[] data, final int len, final char separator) {
		return bytesToHex(data, len, true, separator);
	}

	public static String bytesToHex(final byte[] data, final boolean toUpperCase) {
		return bytesToHex(data, data.length, toUpperCase, DEFAULT_SEPARATOR);
	}

	public static String bytesToHex(final byte[] data, final int len, final boolean toUpperCase) {
		return bytesToHex(data, len, toUpperCase, DEFAULT_SEPARATOR);
	}

	public static String bytesToHex(final byte[] data, final int len, final boolean toUpperCase, final char separator) {
		return bytesToHex(data, len, toUpperCase ? HEX_CHAR_UPPER : HEX_CHAR_LOWER, separator);
	}

	public static String bytesToHex(final byte[] data, final int len, final char[] hexChars, final char separator) {
		if (len < 1 || data.length < len) {
			return "";
		}
		final char[] out;
		if (separator == Character.MIN_VALUE) {
			out = new char[len << 1];
			for (int i = 0, j = 0; i < len; i++) {
				out[j++] = hexChars[(0xF0 & data[i]) >>> 4];
				out[j++] = hexChars[0x0F & data[i]];
			}
			return new String(out);
		} else {
			out = new char[len * 3];
			for (int i = 0, j = 0; i < len; i++) {
				out[j++] = hexChars[(0xF0 & data[i]) >>> 4];
				out[j++] = hexChars[0x0F & data[i]];
				out[j++] = separator;
			}
			return String.copyValueOf(out, 0, out.length - 1);
		}
	}

	private static int toDigit(final char ch, final int index) {
		final int digit = Character.digit(ch, 16);
		return digit;
	}

	public static byte[] hexToBytes(final String hex) {
		return hexToBytes(hex, false);
	}

	public static byte[] hexToBytes(final String hex, final boolean haveSeparator) {
		return hexToBytes(hex.toCharArray(), haveSeparator);
	}

	public static byte[] hexToBytes(final char[] data) {
		return hexToBytes(data, false);
	}

	public static byte[] hexToBytes(final char[] data, final boolean haveSeparator) {
		final int len = data.length;

		if (((len & 0x01) != 0 && !haveSeparator) || (((len % 3) != 2) && ((len % 3) != 0)) && haveSeparator) {
			return new byte[0];
		}

		final byte[] out;
		if (haveSeparator) {
			out = new byte[(len + 1) / 3];

			for (int i = 0, j = 0; j < len; i++) {
				int f = toDigit(data[j], j) << 4;
				j++;
				f = f | toDigit(data[j], j);
				j++;
				out[i] = (byte) (f & 0xFF);
				j++;
			}
		} else {
			out = new byte[len >> 1];
			for (int i = 0, j = 0; j < len; i++) {
				int f = toDigit(data[j], j) << 4;
				j++;
				f = f | toDigit(data[j], j);
				j++;
				out[i] = (byte) (f & 0xFF);
			}
		}
		return out;
	}

	public static void main(String[] args) {
		System.out.println(bytesToHex(hexToBytes("测试测试", false)));
	}

}
