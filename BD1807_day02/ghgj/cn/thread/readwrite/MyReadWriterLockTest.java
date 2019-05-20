package com.ghgj.cn.thread.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyReadWriterLockTest {
	//创建读写锁对象
	static ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();
	public static void main(String[] args) {
		new Thread(){
			@Override
			public void run() {
				//读操作
				set(Thread.currentThread());
				get(Thread.currentThread());
			}
		}.start();
		
		
		new Thread(){
			@Override
			public void run() {
				//写操作
				get(Thread.currentThread());
				set(Thread.currentThread());
			}
		}.start();
	}
	
	//读操作
	public static void get(Thread t){
		//获取读锁  并加锁
		rwl.readLock().lock();
		long start = System.currentTimeMillis();
		try{
			while((System.currentTimeMillis()-start)<3000){
				Thread.sleep(500);
				System.out.println(t.getName()+"====="+"执行读操作");
				
			}
		
		
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println(t.getName()+"====="+"执行读操作完毕");
			rwl.readLock().unlock();
		}
		}

	//写操作
	public static void set(Thread t){
		//获取写锁
		rwl.writeLock().lock();
		long start=System.currentTimeMillis();
		try{
			while(System.currentTimeMillis()-start<2000){
				Thread.sleep(800);
				System.out.println(t.getName()+"*******"+"正在执行写操作");
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println(t.getName()+"*******"+"正在执行写操作完毕");
			rwl.writeLock().unlock();
		}
		
	}
}
