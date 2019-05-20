package com.hdfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class testIO {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		//�ļ��ϴ�   ���� �� ������----hdfs д  �����
		//�������ص�������
		FileInputStream in=new FileInputStream("D:\\movie01.avi");
		//����hdfs�������  fs
		Configuration conf=new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		FSDataOutputStream out = fs.create(new Path("/movie_yy01"));
		//���ж�д  IOUtils hadoop�����ļ���д�Ĺ�����
		//����1��������  ����2�������  ����3�������С
		/*IOUtils.copyBytes(in, out, 1024);
		in.close();
		out.close();*/
		//����1��������  ����2�������  ����3�������С  ����4��ִ�����  �Ƿ�ر���
		IOUtils.copyBytes(in, out, 1024, true);
		//����3---long   ��ȡ���ֽ���  ��ȡָ�����ֽ���
		IOUtils.copyBytes(in, out, 2L, true);
		
		
	}

}
