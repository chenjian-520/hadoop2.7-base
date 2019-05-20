package com.ghgj.cn.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 描述: 观察现象：一个线程获得锁后，另一个线程取不到锁，不会一直等待
 */
public class MyTryLock {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	
	static Lock lock = new ReentrantLock(); // 注意这个地方

	public static void main(String[] args) throws Exception {

		/**
		 * 启动第一个线程
		 */
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				/**
				 * tryLock表示有没有获取到锁  
				 */
				boolean tryLock = lock.tryLock(); // 非阻塞式的
				System.out.println(thread.getName() + "   ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
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
				}
			};
		}.start();

		
		/**
		 * 启动第二个线程
		 */
		new Thread() {
			public void run() {
				
				Thread thread = Thread.currentThread();
				boolean tryLock = lock.tryLock();
				System.out.println(thread.getName() + " ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
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
				}

			};
		}.start();
		
		
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				/**
				 * tryLock表示有没有获取到锁  
				 */
				boolean tryLock = lock.tryLock(); // 非阻塞式的
				System.out.println(thread.getName() + "   ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
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
				}
			};
		}.start();
		
		
		
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				/**
				 * tryLock表示有没有获取到锁  
				 */
				boolean tryLock = lock.tryLock(); // 非阻塞式的
				System.out.println(thread.getName() + "   ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
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
				}
			};
		}.start();
	}
}
