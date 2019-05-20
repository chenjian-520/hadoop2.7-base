package com.ghgj.cn.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * ����: �۲�����һ���̻߳��������һ���߳�ȡ������������һֱ�ȴ�
 */
public class MyTryLock {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	
	static Lock lock = new ReentrantLock(); // ע������ط�

	public static void main(String[] args) throws Exception {

		/**
		 * ������һ���߳�
		 */
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				/**
				 * tryLock��ʾ��û�л�ȡ����  
				 */
				boolean tryLock = lock.tryLock(); // ������ʽ��
				System.out.println(thread.getName() + "   ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
						System.out.println(thread.getName() + "�õ�����");
						for (int i = 0; i < 10; i++) {
							arrayList.add(i);
							System.out.println(thread.getName()+"--"+i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "�ͷ�����");
						lock.unlock();
					}
				}
			};
		}.start();

		
		/**
		 * �����ڶ����߳�
		 */
		new Thread() {
			public void run() {
				
				Thread thread = Thread.currentThread();
				boolean tryLock = lock.tryLock();
				System.out.println(thread.getName() + " ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
						System.out.println(thread.getName() + "�õ�����");
						for (int i = 0; i < 10; i++) {
							arrayList.add(i);
							System.out.println(thread.getName()+"--"+i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "�ͷ�����");
						lock.unlock();
					}
				}

			};
		}.start();
		
		
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				/**
				 * tryLock��ʾ��û�л�ȡ����  
				 */
				boolean tryLock = lock.tryLock(); // ������ʽ��
				System.out.println(thread.getName() + "   ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
						System.out.println(thread.getName() + "�õ�����");
						for (int i = 0; i < 10; i++) {
							arrayList.add(i);
							System.out.println(thread.getName()+"--"+i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "�ͷ�����");
						lock.unlock();
					}
				}
			};
		}.start();
		
		
		
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				/**
				 * tryLock��ʾ��û�л�ȡ����  
				 */
				boolean tryLock = lock.tryLock(); // ������ʽ��
				System.out.println(thread.getName() + "   ******************* " + tryLock);
				
				if (tryLock) {
					try {
						Thread.sleep(500);
						System.out.println(thread.getName() + "�õ�����");
						for (int i = 0; i < 10; i++) {
							arrayList.add(i);
							System.out.println(thread.getName()+"--"+i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "�ͷ�����");
						lock.unlock();
					}
				}
			};
		}.start();
	}
}
