package com.ghgj.cn.thread.implement;

/**
 * 
 * 描述：使用implements Runnable方式实现的线程
 */
public class MyThreadWithImplement implements Runnable {
	int x;

	public MyThreadWithImplement(int x) {
		this.x = x;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println("线程" + name + "的run方法被调用……");
		for (int i = 0; i < 10; i++) {
			System.out.println(name+"-----"+x);
			/*try {
				add(2,3);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
	}
	

	public static void main(String[] args) {
		Thread thread1 = new Thread(new MyThreadWithImplement(1), "thread-1");
		Thread thread2 = new Thread(new MyThreadWithImplement(2), "thread-2");
		
		thread1.start();
		thread2.start();
		
		// 注意调用run和调用start的区别,直接调用run，则都运行在main线程中
	//	thread1.run();
//		thread2.run();
	}
}
