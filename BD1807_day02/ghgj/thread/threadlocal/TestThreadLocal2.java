package com.ghgj.thread.threadlocal;

public class TestThreadLocal2 {
	
	// ��ӡ����
	static void print(String str) {
		
		// ��ӡ��ǰ�̱߳����ڴ���localVariable������ֵ
		System.out.println(str + ":" + localVariable.get());
		// �����ǰ�̱߳����ڴ���localVariable����
//		localVariable.remove();
	}
	
	// ����ThreadLocal����
	static ThreadLocal<String> localVariable = new ThreadLocal<>();
	
	public static void main(String[] args) {

        // �����߳�one
        Thread threadOne = new Thread(new  Runnable() {
            public void run() {
                // �����߳�one�б��ر���localVariable��ֵ
                localVariable.set("threadOne local variable");
                // ���ô�ӡ����
                print("threadOne");
                // ��ӡ���ر���ֵ
                System.out.println("threadOne remove after" + ":" +localVariable.get());

            }
        });
        // �����߳�two
        Thread threadTwo = new Thread(new  Runnable() {
            public void run() {
                // �����߳�one�б��ر���localVariable��ֵ
                localVariable.set("threadTwo local variable");
                // ���ô�ӡ����
                print("threadTwo");
                // ��ӡ���ر���ֵ
                System.out.println("threadTwo remove after" + ":" +localVariable.get());

            }
        });
        // �����߳�
        threadOne.start();
        threadTwo.start();
    }
}