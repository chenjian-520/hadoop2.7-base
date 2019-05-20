package com.ghgj.cn.thread.sync;

/**
 * 
 * ����: ����  Synchronized
 * 
 * 
 * һ������ 3 ���߳�ִ��
 * 
 * �������߳���ʵ  ���� ���еġ�
 */
public class MySynchronized {

	public static void main(String[] args) {

//		final MySynchronized mySynchronized = new MySynchronized();
//		final MySynchronized mySynchronized2 = new MySynchronized();

		/**
		 * ������һ���߳�
		 */
		new Thread("thread1") {
			
			
			
			
			public void run() {
				
				/*try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}*/
				
				/**
				 * ͬ�������
				 */
				synchronized ("sb1") {
					// ���������ִ�е�����ط��� �ͱ����� thread1�߳��Ѿ���ȡ����  sb �����  �����
					try {
						System.out.println(this.getName() + " start");
						synchronized ("sb") {
							
						}
						// ��������쳣��jvm�Ὣ���ͷ�
						int i = 1 / 0; 
						
						Thread.sleep(5000);
						System.out.println(this.getName() + "  5s֮�� ����");
						
						System.out.println(this.getName() + " end");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		

		
		/**
		 * �����ڶ����߳�
		 * 
		 * �����һ���߳���ִ�е�ʱ���׳��쳣����ôִ�и��̵߳�JVM���Զ��ͷų�����̳߳��е�����
		 */
		new Thread("thread2") {
			public void run() {
				
				
				
				
				synchronized ("sb") { 		// ����ͬһ����ʱ���߳�1û�ͷ�֮ǰ���߳�2ֻ�ܵȴ�
//				synchronized ("sb1") { 		//�������һ���������Կ������仰ͬʱ��ӡ
					System.out.println(this.getName() + " start");
					synchronized ("sb1") {
						
					}
					try {
						Thread.sleep(5000);
						System.out.println(this.getName() + "  5s֮�� ����");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println(this.getName() + " end");

				}
			}
		}.start();
		
		
		System.out.println("sssssssssss");
	}
}
