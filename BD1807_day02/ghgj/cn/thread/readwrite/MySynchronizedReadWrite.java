package com.ghgj.cn.thread.readwrite;

/**
 * 
 * ����: һ���߳���Ҫ����Ҫд����synchronize��ʵ�ֵĻ�����д������ֻ����ס��һ���߳�һ���̵߳ؽ���
 */
public class MySynchronizedReadWrite {

	public static void main(String[] args) {
		final MySynchronizedReadWrite test = new MySynchronizedReadWrite();

		new Thread() {
			public void run() {
				test.operate(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				test.operate(Thread.currentThread());
			};
		}.start();

	}

	public synchronized void operate(Thread thread) {
		long start = System.currentTimeMillis();
		int i = 0;
		while (System.currentTimeMillis() - start <= 1) {
			i++;
			if (i % 4 == 0) {
				System.out.println(thread.getName() + "���ڽ���д����");
			} else {
				System.out.println(thread.getName() + "���ڽ��ж�����");
			}
		}
		System.out.println(thread.getName() + "��д�������");
	}

}
