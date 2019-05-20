package com.ghgj.cn.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 描述: 观察现象：一个线程获得锁后，另一个线程取不到锁，不会一直等待
 */
public class MyTryLockWithTime {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	
	static Lock lock = new ReentrantLock(); // 注意这个地方

	public static void main(String[] args) throws Exception {

		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				// 非阻塞式的
				boolean tryLock = false;
				long start = 0;
				try {
					tryLock = lock.tryLock(5, TimeUnit.SECONDS);
					start = System.currentTimeMillis();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} 
				// 如果 3 秒钟内获取到了锁， 那么tryLock将会是true, 如果没有获取到锁，那么tryLock将会是false
				System.out.println(thread.getName() + " ******************* " + tryLock);
				if (tryLock) {
					try {
						System.out.println(thread.getName() + "得到了锁");
						for (int i = 0; i < 5; i++) {
							arrayList.add(i);
							// 如果当前这个线程每次睡眠1s，那就表示其他获取这个锁的线程最多等待3秒钟是获取不到锁的。
							Thread.sleep(1000);
							System.out.println(thread.getName()+"--"+i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "释放了锁");
						long end = System.currentTimeMillis();
						System.out.println("  消耗的总时间： "  + (end - start));
						lock.unlock();
					}
				}
				
				
				/**
				 * 如果当前这个线程获取到了锁之后，执行业务代码，到释放锁， 肯定不会超过 1s 钟
				 */
			};
		}.start();

		
		
		
		
		
		
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				// 非阻塞式的
				boolean tryLock = false;
				try {
					tryLock = lock.tryLock(5, TimeUnit.SECONDS);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} 
				
				System.out.println(thread.getName() + " &&&&&&&&&&&&&&&&&&&& " + tryLock);
				if (tryLock) {
					try {
						System.out.println(thread.getName() + "得到了锁");
						for (int i = 0; i < 5; i++) {
							arrayList.add(i);
							// 如果当前这个线程每次睡眠1s，那就表示其他获取这个锁的线程最多等待3秒钟是获取不到锁的。
							Thread.sleep(1000);
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
