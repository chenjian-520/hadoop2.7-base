package com.ghgj.cn.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 描述: Lock类的测试
 * 
 * 
 * 当前这个主线程中：  启动了两个线程，这两个线程都是通过lock.lock();   这两个线程存在竞争的关系
 */
public class MyLock {
	
	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	
	static Lock lock = new ReentrantLock(); // 注意这个地方

	public static void main(String[] args) {
		
		/**
		 * 启动第一个线程
		 */
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();

				try {
					lock.lock(); // 阻塞式的
					
					System.out.println(thread.getName() + "得到了锁");
					//int s=1/0;
					for (int i = 0; i < 10; i++) {
						arrayList.add(i);
						System.out.println(thread.getName()+"--"+i);
					}
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					System.out.println(thread.getName() + "释放了锁");
					lock.unlock();
				}

			};
		}.start();
		
		
		/**
		 * 两个线程在执行到  lock.lock(); 这句代码的时候去竞争锁，竞争到锁的代码，直到最后在finnaly中 
		 * 通过  lock.unlock();  释放锁
		 */
		
		
		/**
		 * 启动第二个线程
		 */
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				lock.lock(); // 阻塞式的
				try {
					System.out.println(thread.getName() + "得到了锁");
					for (int i = 0; i < 10; i++) {
						arrayList.add(i);
						System.out.println(thread.getName()+"--"+i);
					}
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					System.out.println(thread.getName() + "释放了锁");
					lock.unlock();
				}

			};
		}.start();
	}

}
