package com.ghgj.cn.thread.lock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * ����: �۲�����һ���̻߳��������һ���߳�ȡ������������һֱ�ȴ�
 */
public class MyTryLockWithTime {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	
	static Lock lock = new ReentrantLock(); // ע������ط�

	public static void main(String[] args) throws Exception {

		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				// ������ʽ��
				boolean tryLock = false;
				long start = 0;
				try {
					tryLock = lock.tryLock(5, TimeUnit.SECONDS);
					start = System.currentTimeMillis();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} 
				// ��� 3 �����ڻ�ȡ�������� ��ôtryLock������true, ���û�л�ȡ��������ôtryLock������false
				System.out.println(thread.getName() + " ******************* " + tryLock);
				if (tryLock) {
					try {
						System.out.println(thread.getName() + "�õ�����");
						for (int i = 0; i < 5; i++) {
							arrayList.add(i);
							// �����ǰ����߳�ÿ��˯��1s���Ǿͱ�ʾ������ȡ��������߳����ȴ�3�����ǻ�ȡ�������ġ�
							Thread.sleep(1000);
							System.out.println(thread.getName()+"--"+i);
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						System.out.println(thread.getName() + "�ͷ�����");
						long end = System.currentTimeMillis();
						System.out.println("  ���ĵ���ʱ�䣺 "  + (end - start));
						lock.unlock();
					}
				}
				
				
				/**
				 * �����ǰ����̻߳�ȡ������֮��ִ��ҵ����룬���ͷ����� �϶����ᳬ�� 1s ��
				 */
			};
		}.start();

		
		
		
		
		
		
		new Thread() {
			public void run() {
				Thread thread = Thread.currentThread();
				
				// ������ʽ��
				boolean tryLock = false;
				try {
					tryLock = lock.tryLock(5, TimeUnit.SECONDS);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} 
				
				System.out.println(thread.getName() + " &&&&&&&&&&&&&&&&&&&& " + tryLock);
				if (tryLock) {
					try {
						System.out.println(thread.getName() + "�õ�����");
						for (int i = 0; i < 5; i++) {
							arrayList.add(i);
							// �����ǰ����߳�ÿ��˯��1s���Ǿͱ�ʾ������ȡ��������߳����ȴ�3�����ǻ�ȡ�������ġ�
							Thread.sleep(1000);
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
