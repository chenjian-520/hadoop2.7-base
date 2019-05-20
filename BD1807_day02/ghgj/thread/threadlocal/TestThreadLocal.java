package com.ghgj.thread.threadlocal;

/**
* ������ ThreadLocal��ÿ���̶߳�ӵ��һ�������ĸ��������Բ���
 */
public class TestThreadLocal {

	//��������������������������า��ThreadLocal��initialValue()����ָ����ʼֵ
	//����----��Ҫ����ı���������
	//  int seqNum=0
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		@Override
		//�Ա������г�ʼ���ķ���   ������  i=5;
		protected Integer initialValue() {
			//�������ֵ���Ǳ����ĳ�ʼֵ  ����ֵ----��ʼ����ֵ
			return 0;
		}
	};

	public static void main(String[] args) {
		
		//���������̹߳���seqNum���Բ������к�
		TestThreadLocal sn = new TestThreadLocal();
		new TestThread(sn).start();
		new TestThread(sn).start();
		new TestThread(sn).start();
	}
	
	private static class TestThread extends Thread {
		private TestThreadLocal sn;

		public TestThread(TestThreadLocal sn) {
			this.sn = sn;
		}

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println("thread -" + Thread.currentThread().getName() + " -> " + sn.getNextNum());
			}
		}
	}

	//ͨ��getNextNum��ȡ��һ������ֵ
	public int getNextNum() {
		//���ñ�����ֵ   seqNum++
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}
}
