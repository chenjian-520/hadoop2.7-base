package com.ghgj.cn.thread.volatile_;

/**
 * 
 * ����: volatileֻ��֤�ɼ��Ժ������ԣ�ָ����ţ��� �޷���֤ԭ���ԡ�
 */
public class TestVolatile {

	//int num=0;  volatile   ����֤ԭ���Բ�����
	public static volatile int numb = 0;
	public static volatile boolean b = false;

	public static void main(String[] args) throws Exception {

		// ѭ������100���̡߳����������߼���ÿ���̵߳��ۼӺ���1000�� ���յĽ��Ӧ����10W �����ǽ�����ǡ��ܱ�10WС��
		// ԭ���ǣ�
		for (int i = 0; i < 100; i++) {
			
			// �����߳�
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
