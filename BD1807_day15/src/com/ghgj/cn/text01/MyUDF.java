package com.ghgj.cn.text01;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyUDF	extends UDF {
	/**
	 * trim----返回的是一个去玩前后空格的值
	 * 
	 * 返回值
	 * 就是调用函数的返回值
	 * 参数
	 * 就是我们调用函数的时候给的参数
	 * 注意：
	 * 1）方法必须是public
	 * 2）返回值不能为void
	 * 3）参数列表也不会为空
	 */
	public static int evaluate(int a,int b,int c) {
		// TODO Auto-generated method stub
		return a+b+c;
	}
	//ip 198.168.2.100
	/**
	 * ip_location:ip地址库  起始ip  终止ip   所属国家   所属省份  所属市
	 * log:ip
	 */
	public static String evaluate(String ip){
		String[] datas = ip.split("\\.");
		StringBuffer sb = new StringBuffer();
		for(String s:datas){
			s="000"+s;
			s=s.substring(s.length()-3);
			sb.append(s).append(".");
		}
		return sb.substring(0,sb.length()-1);
	}
	
	
}
