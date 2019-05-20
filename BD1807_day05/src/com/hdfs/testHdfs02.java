package com.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

public class testHdfs02 {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		//key������  value������ֵ
		//System.setProperty("HADOOP_USER_NAME", "hadoop");
		//1.���������ļ�  2.���������ļ��Ĳ���
		/**
		 * �����ļ����ص���Ĭ�ϵ������ļ�
		 * Ĭ�ϼ��ص������ļ���  ������hadoop-hdfs-2.7.6.jar��jar���µ�hdfs-default.xml����ļ�
		 * �����ļ��ϴ���ʱ�� ���еĲ�������Ĭ�ϵĲ���
		 * Configuration����Ĭ�ϼ��أ�
		 * 		hdfs-default.xml
		 * 		mapred-default.xml
		 * 		yarn-default.xml
		 * 		core-default.xml
		 * ��Ҫ�����Լ��������ļ�����Ҫ���Լ��������ļ����������̵�src��  ���ܣ�Ĭ�ϼ��ص��Լ��������ļ�
		 * 		�Զ����ص������ļ�ֻ��ʶ��
		 * 			hdfs-default.xml
		 * 			hdfs-site.xml
		 * 			mapred-default.xml
		 * 			mapred-site.xml
		 * 			yarn-default.xml
		 * 			core-default.xml
		 *  * 		yarn-site.xml
		 * 			core-site.xml
		 * �������û�з���src�£���Ҫ�������ֶ����������ļ�
		 * 		conf.addResource("conf/hdfs-site.xml");
		 * 		�ڴ�����Ҳ�����ֶ����������ļ�
		 * �����ļ��ļ���˳��
		 * 	1��jar��
		 * 2��src��
		 * 3�������е�
		 * ��Ч��
		 * 	3������2��������1
		 * 			
		 */
		Configuration conf=new Configuration();
		//conf.addResource("conf/hdfs-site.xml");
		conf.set("dfs.replication", "4");
		//û��ָ����Ⱥ���������  ��Ҫ��ȡ��Ⱥ����Ҫָ����Ⱥ���������  
		//��conf�����������������
		/**
		 * ����1�������ļ���������
		 * ����2�������ļ������Ե�ֵ
		 */
		//conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
		//����1��namenode���������
		FileSystem fs=FileSystem.get(new URI("hdfs://192.168.66.131:9000"), conf, "chen");
		//DFS[DFSClient[clientName=DFSClient_NONMAPREDUCE_-1816936482_1, ugi=Administrator (auth:SIMPLE)]]
		//��λ�ȡ�Ķ����Ƿֲ�ʽ�ļ�ϵͳ�Ķ���
		//���صĶ��������DistributedFileSystem
		System.out.println(fs instanceof DistributedFileSystem);
		//�ļ��ϴ�
		Path src=new Path("G:\\movies.dat");
		Path dst=new Path("/");
		/**
		 * �ļ��ϴ���ʱ��  ��һ��Ȩ�޵�����  ԭ�����û���eclipse�н����ϴ��ļ���ʱ��  ʹ��windows�µ��û�  ������hdfs�İ�װ�û�
		 * ���Ȩ�����⣺
		 * 	1���ڴ����ύ��ʱ��ָ���û�
		 * 	�������е�ʱ��  �Ҽ����� run configurations>> �������д�����Ҫ�Ĳ���
		 * 		program arguments:�������������Ҫ�Ĳ���
		 * 			��������Ҫ����̨����Ĳ���  �������ͨ��main(String[] args)
		 *		VM arguments:JVM���й�������Ҫ�Ĳ���
		 *			����jvm�ڴ��С   jvm���е��û�ָ��
		 			-DHADOOP_USER_NAME=hadoop
		 	2���ڴ�����ָ���û�
		 		1)ָ��ϵͳ�����в���  System
		 		System.setProperty("HADOOP_USER_NAME", "hadoop");
		 		2)FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		 	3)windows�����һ��hadoop�û�  ������			 		
		 * 	
		 */
		fs.copyFromLocalFile(src, dst);
		fs.close();
	}

}
