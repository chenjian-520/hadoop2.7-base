package com.ghgj.cn.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * ����:  
 * 		�۲��������thread-0�õ�����������������
 * 		thread-1���Ի�ȡ��������ò�������ȴ������ǿ��Ա��жϵȴ�
 */
public class MyLockInterruptibly {
	
	public void insert(Thread thread) throws InterruptedException {
		// ע�⣬�����Ҫ��ȷ�жϵȴ������̣߳����뽫��ȡ���������棬Ȼ��InterruptedException�׳�
		lock.lockInterruptibly(); 
		try {
			System.out.println(thread.getName() + "�õ�����");
			long startTime = System.currentTimeMillis();
			for (;;) {
				// һֱ������   for  ��
				if (System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
					break;
				// ��������
			}
			
			//Thread.sleep(10000);
			
			
		} finally {
			System.out.println(Thread.currentThread().getName() + "ִ��finally");
			lock.unlock();
			System.out.println(thread.getName() + "�ͷ�����");
		}
	}

	/**
	 *  lock��
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

		// ����ȷ�������߳�һ����ִ�е���   lock.lockInterruptibly(); 
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// �����жϷ����������ܷ��жϵȴ��е��߳�
		//�߳��ж�    ����̱߳��ж�   ����׳��쳣  catch
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
			System.out.println(Thread.currentThread().getName() + "���ж�");
		}
	}

}
