package com.ghgj.cn.test01;

public class SingleDemo {
	private SingleDemo(){
		
	}
	//����
	private SingleDemo single=new SingleDemo();
	public SingleDemo getInstance(){
		//single=new SingleDemo();
		return single;
	}
	
	/**
	 * 1.��֤���˶��̲߳�����ʱ�� ����һ������
	 * 2.���ܵ�����
	 */

}
