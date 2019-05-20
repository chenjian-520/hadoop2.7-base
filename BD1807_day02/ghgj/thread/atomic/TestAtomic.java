package com.ghgj.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *atomic��֤ԭ���Բ���
 */
public class TestAtomic {

	/**
	 * ����һ������
	 *  AtomicInteger  
	 *  int   long   boolean
	 *  AtomicInteger-----int/integer
	 *  int numb=0;  
	 *  ���������ʱ�� ����  �൱�ڳ�ʼ��numb����
	 */
	public static AtomicInteger numb = new AtomicInteger(0);
	//public static int numb = 0; ���ַ�ʽ���ܱ�֤ԭ����

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						
						// incrementAndGet   numb++  ԭ���Ե�
						numb.incrementAndGet();
					}
				}
			}).start();

		}

		Thread.sleep(2000);
		System.out.println(numb);
	}

}
