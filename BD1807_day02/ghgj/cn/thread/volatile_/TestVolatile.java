package com.ghgj.cn.thread.volatile_;

/**
 * 
 * 描述: volatile只保证可见性和有序性（指令不重排）， 无法保证原子性。
 */
public class TestVolatile {

	//int num=0;  volatile   不保证原子性操作的
	public static volatile int numb = 0;
	public static volatile boolean b = false;

	public static void main(String[] args) throws Exception {

		// 循环启动100个线程。按照正常逻辑，每个线程的累加和是1000， 最终的结果应该是10W ，但是结果不是。总比10W小。
		// 原因是？
		for (int i = 0; i < 100; i++) {
			
			// 启动线程
			new Thread(new Runnable() {
				@Override
				public void run() {
						for (int i = 0; i < 1000; i++) {
							//b=true;
							numb=numb+1;
						}
					
				}
			}).start();
		}

		Thread.sleep(1000);
		System.out.println(numb);
	}
}
