package com.xishatou.utils;

public class GetCharAscii {  

	/**
	 * 把16进制字符转换成汉字
	 * @param s
	 * @return
	 */
	public static String hexToStringGBK(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		try {
			s = new String(baKeyword, "gb2312");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
			return "";
		}
		return s;
	} 
	
	/**
	 * 汉字转换成 gb2312 的ascii码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String wordToAsciiGB2312(String str) throws Exception{
		byte[] temp  = str.getBytes("utf-8"); //这里写原编码的方式
		byte[] newTemp = new String(temp,"utf-8").getBytes("gbk");//这里转换后的编码方式
		String s = BytesUtils.BytesToHex(newTemp).replace(" ","").trim();
		return s;
	}
	
	/**
	 * 右补位
	 * @param oriStr 原字符串
	 * @param len 目标字符串长度
	 * @param alexin 补位字符
	 * @return 目标字符串
	 */
	public static String padRight(String oriStr,int len,char alexin){
		int strlen = oriStr.length();
		if(strlen < len){
			for(int i=0;i<len-strlen;i++){
				oriStr += alexin;
			}
		}
		return oriStr;
	}

	/**
	 * 左补位
	 * @param oriStr 原字符串
	 * @param len 目标字符串长度
	 * @param alexin 补位字符
	 * @return 目标字符串
	 */
	public static String padLeft(String oriStr,int len,char alexin){
		int strlen = oriStr.length();
		if(strlen < len){
			for(int i=0;i<len-strlen;i++){
				oriStr = alexin + oriStr;
			}
		}
		return oriStr;
	}

	
	
	public static void main(String[] args) throws Exception {
			String aa = "北京欢迎你A1@";
			/*byte[] temp  = aa.getBytes("utf-8"); //这里写原编码的方式
			byte[] newTemp = new String(temp,"utf-8").getBytes("gbk");//这里转换后的编码方式
			String s = BytesUtils.BytesToHex(newTemp);*/
			System.out.println(wordToAsciiGB2312("张三"));
			System.out.println(padRight(wordToAsciiGB2312("张三"), 40, '0'));
			System.out.println(hexToStringGBK("BEA93141000000000000BEA93141000000000000"));

	}
}  