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
		//文件上传   本地 读 输入流----hdfs 写  输出流
		//创建本地的输入流
		FileInputStream in=new FileInputStream("D:\\movie01.avi");
		//创建hdfs的输出流  fs
		Configuration conf=new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf, "hadoop");
		FSDataOutputStream out = fs.create(new Path("/movie_yy01"));
		//进行读写  IOUtils hadoop进行文件读写的工具类
		//参数1：输入流  参数2：输出流  参数3：缓冲大小
		/*IOUtils.copyBytes(in, out, 1024);
		in.close();
		out.close();*/
		//参数1：输入流  参数2：输出流  参数3：缓冲大小  参数4：执行完成  是否关闭流
		IOUtils.copyBytes(in, out, 1024, true);
		//参数3---long   读取的字节数  读取指定的字节数
		IOUtils.copyBytes(in, out, 2L, true);
		
		
	}

}
