package com.aura.hbase.day07;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	public static String getNow(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
		
	}
	
	public static void main(String[] args) {
		
		
		System.out.println(DateTimeUtil.getNow());
		
	}

	public static String getServerName() {
		// TODO Auto-generated method stub
		return "55";
	}
}
