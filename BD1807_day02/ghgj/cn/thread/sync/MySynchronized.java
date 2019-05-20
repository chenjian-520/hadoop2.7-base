package com.ghgj.cn.thread.sync;

/**
 * 
 * 描述: 测试  Synchronized
 * 
 * 
 * 一共会有 3 个线程执行
 * 
 * 这三个线程其实  并行 运行的。
 */
public class MySynchronized {

	public static void main(String[] args) {

//		final MySynchronized mySynchronized = new MySynchronized();
//		final MySynchronized mySynchronized2 = new MySynchronized();

		/**
		 * 启动第一个线程
		 */
		new Thread("thread1") {
			
			
			
			
			public void run() {
				
				/*try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}*/
				
				/**
				 * 同步代码块
				 */
				synchronized ("sb1") {
					// 如果代码能执行到这个地方。 就表明。 thread1线程已经获取到了  sb 对象的  锁标记
					try {
						System.out.println(this.getName() + " start");
						synchronized ("sb") {
							
						}
						// 如果发生异常，jvm会将锁释放
						int i = 1 / 0; 
						
						Thread.sleep(5000);
						System.out.println(this.getName() + "  5s之后， 醒了");
						
						System.out.println(this.getName() + " end");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		

		
		/**
		 * 启动第二个线程
		 * 
		 * 如果第一个线程在执行的时候抛出异常，那么执行该线程的JVM会自动释放出这个线程持有的锁。
		 */
		new Thread("thread2") {
			public void run() {
				
				
				
				
				synchronized ("sb") { 		// 争抢同一把锁时，线程1没释放之前，线程2只能等待
//				synchronized ("sb1") { 		//如果不是一把锁，可以看到两句话同时打印
					System.out.println(this.getName() + " start");
					synchronized ("sb1") {
						
					}
					try {
						Thread.sleep(5000);
						System.out.println(this.getName() + "  5s之后， 醒了");
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
