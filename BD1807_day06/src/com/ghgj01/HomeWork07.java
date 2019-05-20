package com.ghgj01;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.io.IOUtils;

/*
 * ��ȡ�ڶ�����
 * �ڶ������ƫ����
 * �ڶ�����ĳ���
 * hdfs   ����----hdfs  ���
 */
public class HomeWork07 {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		Path p=new Path("/test/hadoop-2.7.6.tar.gz");
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(p, false);
		LocatedFileStatus next = listFiles.next();
		//�������洢�Ľ��������ݺͿ�Ķ�Ӧ��ϵ  blk_001   blk_002
		BlockLocation[] blockLocations = next.getBlockLocations();
		//�������Ϣ  ����Ϣ  �и�����Ϣ
		BlockLocation secondary=blockLocations[1];
		long offset = secondary.getOffset();
		long length = secondary.getLength();
		//hdfs��������
		FSDataInputStream open = fs.open(p);
		open.seek(offset);
		//hdfs�������
		FSDataOutputStream create = fs.create(new Path("/testsecon01"));
		IOUtils.copyBytes(open, create, length, true);
	}

}
