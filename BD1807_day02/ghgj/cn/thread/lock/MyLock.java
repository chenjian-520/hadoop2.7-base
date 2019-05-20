package com.ghgj.cn.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * ����: Lock��Ĳ���
 * 
 * 
 * ��ǰ������߳��У�  �����������̣߳��������̶߳���ͨ��lock.lock();   �������̴߳��ھ����Ĺ�ϵ
 */
public class MyLock {
	
	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	
	static Lock lock = new ReentrantLock(); // ע������ط�

	public static void main(String[] args) {
		
		/**
		 * ������һ���߳�
		 */
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();

				try {
					lock.lock(); // ����ʽ��
					
					System.out.println(thread.getName() + "�õ�����");
					//int s=1/0;
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

			};
		}.start();
		
		
		/**
		 * �����߳���ִ�е�  lock.lock(); �������ʱ��ȥ�����������������Ĵ��룬ֱ�������finnaly�� 
		 * ͨ��  lock.unlock();  �ͷ���
		 */
		
		
		/**
		 * �����ڶ����߳�
		 */
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				lock.lock(); // ����ʽ��
				try {
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

			};
		}.start();
	}

}
