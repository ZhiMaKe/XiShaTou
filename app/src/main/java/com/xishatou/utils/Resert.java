package com.xishatou.utils;

/**
 *  发送报文封装工具
 * @author Admin
 *
 */
public class Resert {
	/**
	 * 把十六进制的字符串转换成十进制的数组
	 * @param str 十六进制的字符串
	 * @return
	 */
	public static int[] arryInt_str(String str){
		String[] strs=new String[str.length()/2];
		int[] char2=new int[str.length()/2];
		for (int i = 0; i < str.length()/2; i++) {
			strs[i]=str.substring(i*2, i*2+2);
			char2[i]=Integer.parseInt(strs[i],16);
		}
		return char2;
	}
	
	/**
	 * 得到一个十六进制的异或校验值
	 * @param str 
	 * @return
	 */
	public static String value_arryInt(String str){
		String str_int=null;
		int[] arry=arryInt_str(str);
		int num=0;
		for (int i = 0; i < arry.length; i++) {
			num=num^arry[i];
		}
		str_int = Integer.toHexString(num).length()==2?Integer.toHexString(num):"0"+Integer.toHexString(num);
		return str_int.toUpperCase();
	}

	
	/**
	 * 发送的数据报文7E协议
	 * @param msyHead 消息头
	 * @param msgbody 消息体
	 * @return
	 */
	public static String sendData(String msyHead,String msgbody){
		String xor= value_arryInt(msyHead+msgbody); //异或值
		String result_data = (msyHead + msgbody + xor).toUpperCase();
		String[] arr = new String[result_data.length()/2];
		String reulst="";
		for(int i=0 ;i<result_data.length()/2;i++){
			arr[i]=result_data.substring(i*2,i*2+2);
			if(arr[i].equals("7E")){
				reulst+="7D02";
			}else if(arr[i].equals("7D")){
				reulst+="7D01";
			}else{
				reulst+=arr[i];
			}
		}
		return "7E"+reulst+"7E";
	}
}    




