package com.ghgj01;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/*
 * ������ط���ʼ���������ⳤ��
 * hdfs   ����----hdfs  ���
 */
public class HomeWork06 {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		//hdfs��������
		FSDataInputStream open = fs.open(new Path("/ss02"));
		open.seek(5);
		//hdfs�������
		FSDataOutputStream create = fs.create(new Path("/testss01"));
		IOUtils.copyBytes(open, create, 10L, true);
	}

}