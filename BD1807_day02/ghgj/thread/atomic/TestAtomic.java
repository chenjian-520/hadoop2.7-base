package com.ghgj.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *atomic保证原子性操作
 */
public class TestAtomic {

	/**
	 * 定义一个变量
	 *  AtomicInteger  
	 *  int   long   boolean
	 *  AtomicInteger-----int/integer
	 *  int numb=0;  
	 *  创建对象的时候 参数  相当于初始化numb变量
	 */
	public static AtomicInteger numb = new AtomicInteger(0);
	//public static int numb = 0; 这种方式不能保证原子性

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						
						// incrementAndGet   numb++  原子性的
						numb.incrementAndGet();
					}
				}
			}).start();

		}

		Thread.sleep(2000);
		System.out.println(numb);
	}

}
