package com.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class testHdfs01 {
	public static void main(String[] args) throws IOException {
		//����hdfs�������ļ�
		/**
		 * Configuration  �����ļ�����    ���������ļ�  �����������ļ�
		 * ����������ļ����ص��Ǽ�Ⱥ�ģ�  ���Ǳ��صģ�
		 */
		Configuration conf=new Configuration();
		//��ȡhdfs�Ĳ�������   ��ȡ����hdfs�Ĳ����������  FileSystem����
		/**
		 * hdfs���ļ�ϵͳ����   ��Ҫ����hdfs�������õ��������
		 * hadoop fs
		 * �ײ�IO����
		 * ���fs������˭���ļ�ϵͳ����  ��Ⱥ�ģ����صģ�
		 */
		FileSystem fs=FileSystem.get(conf);
		//org.apache.hadoop.fs.LocalFileSystem@5bd03f44  �����ļ�ϵͳ����
		//�ļ��ϴ�  ������  ���ڱ��ز�����
		System.out.println(fs);
		
		//����1---�����ļ� windows   ����2---Ŀ���ļ�·��   --hdfs
		//Path   hdfs���ļ�����  ·������   java--File
		Path src=new Path("G:\\movies.dat");
		Path dst=new Path("/");
		//�ļ��ϴ�
				/**
				 * put ����  hdfs·��
				 * copyfromlocal-----api
				 * movefromlocal
				 */
		//�ļ��ϴ�  �ϴ�������     �ļ������˹��̵ĸ�Ŀ¼��
		//�ļ��ϴ���ʱ���������������ļ�   ԭʼ�ļ�movie.dat   ����һ���ļ�  .movies.dat.crc  ������ʲô��
		fs.copyToLocalFile(false,src, dst,true);
		fs.close();

	}

}