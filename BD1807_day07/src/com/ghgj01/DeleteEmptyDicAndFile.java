package com.ghgj01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class DeleteEmptyDicAndFile {
/**
 * 删除HDFS集群中的所有空文件 和空文件夹
 * @param args
 * 判断给定的目录是否为空
 * 为空 
 * 	删除
 * 不为空
 * 	循环变量所有的文件或目录
 * 	如果是文件删除
 *  如果是目录 递归
 *  判断原来的目录是否为空  空删除
 * @throws URISyntaxException 
 * @throws InterruptedException 
 * @throws IOException 
 */
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://192.168.66.131:9000"), conf, "chen");
		Path path = new Path("/");
		DeleteEmpty(path,fs);
	}

	public static void DeleteEmpty(Path path, FileSystem fs) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		FileStatus[] listStatus = fs.listStatus(path);
		if(listStatus.length==0){
			fs.delete(path,false);
		}else{
			for(FileStatus fss:listStatus){
				Path path2 = fss.getPath();
				if(fss.isFile()){
					long len = fss.getLen();
					if(len==0){
						fs.delete(path2,false);
					}
				}else{
					DeleteEmpty(path2, fs);
				}
			}
			FileStatus[] new_listStatus = fs.listStatus(path);
			if(listStatus.length==0){
				fs.delete(path,false);
			}
		}
	}
	
}
