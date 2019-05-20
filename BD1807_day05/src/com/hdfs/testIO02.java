package com.hdfs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class testIO02 {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		//�ļ�����   hdfs  ��  ������---���� д  �����
		//hdfs��������  fs
		Configuration conf=new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		FSDataInputStream in = fs.open(new Path("/movie_yy"));
		//�������������ָ�����õ�ĳһ���ֽڿ�ʼ��ȡ  ����--ƫ����
		in.seek(10L);
		//�������ص������
		FileOutputStream out=new FileOutputStream("D:\\moo02");
		//�������ĸ���
		IOUtils.copyBytes(in, out, 100L, true);
		
	}

}
