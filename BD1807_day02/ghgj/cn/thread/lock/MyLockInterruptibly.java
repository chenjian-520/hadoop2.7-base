package com.ghgj.cn.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 描述:  
 * 		观察现象：如果thread-0得到了锁，阻塞。。。
 * 		thread-1尝试获取锁，如果拿不到，会等待，但是可以被中断等待
 */
public class MyLockInterruptibly {
	
	public void insert(Thread thread) throws InterruptedException {
		// 注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
		lock.lockInterruptibly(); 
		try {
			System.out.println(thread.getName() + "得到了锁");
			long startTime = System.currentTimeMillis();
			for (;;) {
				// 一直运行在   for  中
				if (System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
					break;
				// 插入数据
			}
			
			//Thread.sleep(10000);
			
			
		} finally {
			System.out.println(Thread.currentThread().getName() + "执行finally");
			lock.unlock();
			System.out.println(thread.getName() + "释放了锁");
		}
	}

	/**
	 *  lock锁
	 */
	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		MyLockInterruptibly test = new MyLockInterruptibly();
		
		
		MyThread thread0 = new MyThread(test);
		MyThread thread1 = new MyThread(test);
		thread0.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread1.start();

		// 就是确保两个线程一定都执行到了   lock.lockInterruptibly(); 
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 调用中断方法来测试能否中断等待中的线程
		//线程中断    如果线程被中断   则会抛出异常  catch
		thread1.interrupt();
		System.out.println("=====================");
	}

	
}

class MyThread extends Thread {
	
	private MyLockInterruptibly test = null;

	public MyThread(MyLockInterruptibly test) {
		this.test = test;
	}

	@Override
	public void run() {

		try {
			test.insert(Thread.currentThread());
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + "被中断");
		}
	}

}
