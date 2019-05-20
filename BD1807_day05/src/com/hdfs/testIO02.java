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
		//文件下载   hdfs  读  输入流---本地 写  输出流
		//hdfs的输入流  fs
		Configuration conf=new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		FSDataInputStream in = fs.open(new Path("/movie_yy"));
		//这个方法将流的指针设置到某一个字节开始读取  参数--偏移量
		in.seek(10L);
		//创建本地的输出流
		FileOutputStream out=new FileOutputStream("D:\\moo02");
		//进行流的复制
		IOUtils.copyBytes(in, out, 100L, true);
		
	}

}
