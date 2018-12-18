package com.xishatou.udp;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class EchoClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
			throws Exception {
		
		
		//服务器推送对方IP和PORT
		ByteBuf buf = (ByteBuf) packet.copy().content();
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		// String str = new String(req, "UTF-8");
		String str = bytesToHexString(req);
		
		int msgType = Integer.parseInt(str.substring(0,4));//消息类型码
			
		System.out.println("接受的消息为："+str);

		Thread.sleep(10000);
		//发送消息
		byte[] bytes = new byte[0];
		bytes=AppendBCDString(bytes, "3001000400000000000100000000");
		DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
		ctx.writeAndFlush(data);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端向服务器发送自己的IP和PORT");
		//byte[] bytes = ("1001000400000000000112345678").getBytes("UTF-8");
		byte[] bytes = new byte[0];
		bytes=AppendBCDString(bytes, "1001000400000000000112345678");
		InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 9999);
		DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), addr);
		ctx.writeAndFlush(data);
		/*ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer("R".getBytes()), 
                new InetSocketAddress("127.0.0.1", 8888)));
        super.channelActive(ctx);*/
	}


	/**  
	 * 把字节数组转换成16进制字符串  
	 * @param bArray  
	 * @return  
	 */   
	public static final String bytesToHexString(byte[] bArray) {   
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

}