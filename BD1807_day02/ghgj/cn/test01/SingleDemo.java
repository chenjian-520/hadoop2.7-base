package com.ghgj.cn.test01;

public class SingleDemo {
	private SingleDemo(){
		
	}
	//懒汉
	private SingleDemo single=new SingleDemo();
	public SingleDemo getInstance(){
		//single=new SingleDemo();
		return single;
	}
	
	/**
	 * 1.保证不了多线程操作的时候 创建一个对象
	 * 2.性能的问题
	 */

}
