package com.ghgj.cn.text01;

import org.apache.hadoop.hive.ql.exec.UDF;

public class MyUDF	extends UDF {
	/**
	 * trim----���ص���һ��ȥ��ǰ��ո��ֵ
	 * 
	 * ����ֵ
	 * ���ǵ��ú����ķ���ֵ
	 * ����
	 * �������ǵ��ú�����ʱ����Ĳ���
	 * ע�⣺
	 * 1������������public
	 * 2������ֵ����Ϊvoid
	 * 3�������б�Ҳ����Ϊ��
	 */
	public static int evaluate(int a,int b,int c) {
		// TODO Auto-generated method stub
		return a+b+c;
	}
	//ip 198.168.2.100
	/**
	 * ip_location:ip��ַ��  ��ʼip  ��ֹip   ��������   ����ʡ��  ������
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
