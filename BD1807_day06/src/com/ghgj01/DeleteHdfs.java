package com.ghgj01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class DeleteHdfs {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(new URI("hdfs://192.168.66.131:9000"), conf, "chen");
		Path p = new Path("/hadoop");
		delete(p,fs);
	}

	public static void delete(Path path,FileSystem fs) throws IOException, InterruptedException, URISyntaxException, FileNotFoundException {

		FileStatus[] listStatus = fs.listStatus(path);
		if(listStatus.length==0){
			fs.delete(path,false);
		}else{
			for(FileStatus fss:listStatus){
				Path p = fss.getPath();
				if(fss.isFile()){
					fs.delete(p,false);
				}else{
					delete(p, fs);
				}
			}
		}
		FileStatus[] listStatus1 = fs.listStatus(path);
		if(listStatus1.length==0){
			fs.delete(path,false);
		}
	}

}