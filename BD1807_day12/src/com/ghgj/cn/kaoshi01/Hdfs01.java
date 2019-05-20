package com.ghgj.cn.kaoshi01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class Hdfs01 {
	static int count =0;
	static int sum = 0;
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://192.168.66.131:9000"), conf, "chen");
		Path p = new Path("/");
		tongji(p,fs);
		double avg = (double)count/(double)sum;
		System.out.println(sum+"   "+count);
		System.out.println("文件占比："+avg*100+"%");
	}

	public static void tongji(Path path,FileSystem fs) throws IOException, InterruptedException, URISyntaxException, FileNotFoundException {

		FileStatus[] listStatus = fs.listStatus(path);
		if(listStatus.length==0){
			
		}else{
			for(FileStatus fss:listStatus){
				Path p = fss.getPath();
				if(fss.isFile()){
					if(fss.getLen()<fss.getBlockSize()){
						count++;
					}else{
						sum++;
					}
				}else{
					tongji(p,fs);
				}
			}
		}
	}
}
