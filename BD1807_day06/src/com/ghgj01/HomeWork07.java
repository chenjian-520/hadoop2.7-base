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
 * 读取第二个块
 * 第二个快的偏移量
 * 第二个快的长度
 * hdfs   输入----hdfs  输出
 */
public class HomeWork07 {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		Path p=new Path("/test/hadoop-2.7.6.tar.gz");
		RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(p, false);
		LocatedFileStatus next = listFiles.next();
		//这个数组存储的仅仅是数据和块的对应关系  blk_001   blk_002
		BlockLocation[] blockLocations = next.getBlockLocations();
		//这个块信息  块信息  有副本信息
		BlockLocation secondary=blockLocations[1];
		long offset = secondary.getOffset();
		long length = secondary.getLength();
		//hdfs的输入流
		FSDataInputStream open = fs.open(p);
		open.seek(offset);
		//hdfs的输出流
		FSDataOutputStream create = fs.create(new Path("/testsecon01"));
		IOUtils.copyBytes(open, create, length, true);
	}

}
