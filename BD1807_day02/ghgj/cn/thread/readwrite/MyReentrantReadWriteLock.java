package com.ghgj.cn.thread.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * ʹ�ö�д��������ʵ�ֶ�д�����������������������У�д�������������߳�
 * 
 * �����һ���߳��Ѿ�ռ���˶��������ʱ�����߳����Ҫ����д����������д�����̻߳�һֱ�ȴ��ͷŶ�����
 * �����һ���߳��Ѿ�ռ�ö����������̻߳����������������Բ�������
 * �����һ���߳��Ѿ�ռ����д�������ʱ�����߳��������д�����߶�������������̻߳�һֱ�ȴ��ͷ�д����
 */
public class MyReentrantReadWriteLock {
	
	// ��ʼ��һ����д������
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		
		final MyReentrantReadWriteLock test = new MyReentrantReadWriteLock();

		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
				test.write(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				test.write(Thread.currentThread());
				test.get(Thread.currentThread());
			};
		}.start();

	}

	/**
	 * ������,�ö���������
	 */
	public void get(Thread thread) {
		// ��Ӷ���
		rwl.readLock().lock();
		try {
			long start = System.currentTimeMillis();

			while (System.currentTimeMillis() - start <= 1000) {
				System.out.println(thread.getName() + "���ڽ��ж�����");
				Thread.sleep(100);
			}
			System.out.println(thread.getName() + "���������");
		} catch (Exception e) {
		} finally {
			// �ͷŶ���
			rwl.readLock().unlock();
		}
	}

	/**
	 * д��������д��������
	 */
	public void write(Thread thread) {
		// ���д��
		rwl.writeLock().lock();
		try {
			long start = System.currentTimeMillis();

			while (System.currentTimeMillis() - start <= 1) {
				System.out.println(thread.getName() + "���ڽ���д����");
			}
			System.out.println(thread.getName() + "д�������");
		} finally {
			// �ͷ�д��
			rwl.writeLock().unlock();
		}
	}
}
