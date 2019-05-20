package com.ghgj.cn.thread.extend;

import java.util.Random;

/**
 * 
 * extends  Thread创建线程
 */
public class MyThreadWithExtends extends Thread {
	
	
//	private List<String> strList;
	
	String flag;

	public MyThreadWithExtends(String flag) {
		this.flag = flag;
	}

	/**
	 *线程启动的时候调用的方法
	 */
	@Override
	public void run() {
		// 获取线程的名称  thread-0   
		String tname = Thread.currentThread().getName();
		System.out.println(tname + "正在运行");
		Random random = new Random();
		
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(random.nextInt(50) * 50);
				System.out.println(tname + "...." + flag);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Thread thread1 = new MyThreadWithExtends("a");
		Thread thread2 = new MyThreadWithExtends("b");
		
		/**
		 * 启动两个线程
		 */
		//thread1.start();
		//thread2.start();
		
		/**
		 * 这个方法仅仅相当于  普通方法的调用
		 */
		thread1.run();
		thread2.run();
	}
}
