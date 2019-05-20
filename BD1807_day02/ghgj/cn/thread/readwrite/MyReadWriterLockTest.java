package com.ghgj.cn.thread.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyReadWriterLockTest {
	//������д������
	static ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();
	public static void main(String[] args) {
		new Thread(){
			@Override
			public void run() {
				//������
				set(Thread.currentThread());
				get(Thread.currentThread());
			}
		}.start();
		
		
		new Thread(){
			@Override
			public void run() {
				//д����
				get(Thread.currentThread());
				set(Thread.currentThread());
			}
		}.start();
	}
	
	//������
	public static void get(Thread t){
		//��ȡ����  ������
		rwl.readLock().lock();
		long start = System.currentTimeMillis();
		try{
			while((System.currentTimeMillis()-start)<3000){
				Thread.sleep(500);
				System.out.println(t.getName()+"====="+"ִ�ж�����");
				
			}
		
		
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println(t.getName()+"====="+"ִ�ж��������");
			rwl.readLock().unlock();
		}
		}

	//д����
	public static void set(Thread t){
		//��ȡд��
		rwl.writeLock().lock();
		long start=System.currentTimeMillis();
		try{
			while(System.currentTimeMillis()-start<2000){
				Thread.sleep(800);
				System.out.println(t.getName()+"*******"+"����ִ��д����");
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println(t.getName()+"*******"+"����ִ��д�������");
			rwl.writeLock().unlock();
		}
		
	}
}
