package com.aura.hbase.nio;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务端部署的程序
 * 
 * 客户端发送请求，其实就是想得到服务器的系统时间，客户端保持和服务端的时间同步
 */
public class DateTimeUtil {

	public static String getNow(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
		
	}
	
	public static void main(String[] args) {
		
		
		System.out.println(DateTimeUtil.getNow());
		
	}
}
