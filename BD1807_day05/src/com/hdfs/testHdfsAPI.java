package com.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

public class testHdfsAPI {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Configuration conf=new Configuration();
		//fs
		FileSystem fs=FileSystem.get(new URI("hdfs://192.168.66.131:9000"), conf, "hadoop");
		//文件上传  本地---hdfs
		//fs.copyFromLocalFile(new Path("D:\\movies.dat"), new Path("/movie_tt"));
		//文件下载
		//fs.copyToLocalFile(new Path("/movie_tt"), new Path("D:\\movie01.avi"));
		//创建文件夹  hdfs dfs -mkdir    级联创建文件夹
		//boolean isfinish=fs.mkdirs(new Path("/bd1807/aa/ss/ff"));
		//System.out.println(isfinish);
		
		//删除文件夹  或文件  返回值   bool  删除--true   否则--false
		//delete() 既可以删除文件  也可以删除文件夹的
		//System.out.println(fs.delete(new Path("/aa001")));
		//删除文件夹
		//System.out.println(fs.delete(new Path("/bd1807")));
		/**
		 * 参数1：需要删除的路径  文件   文件夹
		 * 参数2：是否需要级联删除  true需要  false  --不需要
		 */
		//fs.delete(new Path("/ss"), false);
		//fs.delete(new Path("/ss"),true);
		//判断文件夹/文件是否存在  如果存在  则返回true   否则--false
		//boolean ise=fs.exists(new Path("/bd1807"));   //mkdir/delete
		//System.out.println(ise);
		/*if(fs.exists(new Path("/movie_tt"))){
			fs.delete(new Path("/movie_tt"),false);
		}*/
		//查看文件的信息
		//1.查看文件的信息  指定路径下的文件信息  参数1--需要查看的路径  参数2--是否级联  ls -R
		/*RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), false);
		//迭代器 迭代   hasNext   next()
		//LocatedFileStatus  文件状态对象   封装的文件状态 -- 文件的路径 文件的大小  文件的用户  组  文件的副本
		while(listFiles.hasNext()){
			System.out.println("------------------------------");
			//next对象  获取的是文件的状态信息  一个next代表的是一个文件
			LocatedFileStatus next = listFiles.next();
			System.out.println(next.getPath());
			System.out.println(next.getBlockSize());//获取的配置文件的分块大小   128M
			System.out.println(next.getLen());//获取文件的真实大小  块的实际大小
			System.out.println(next.getReplication());
			//获取的是文件的切块信息  每一个文件的切块信息
			//BlockLocation   数据块的封装对象   包括数据块的存储位置  数据块的大小  数据块的所属用户  组
			BlockLocation[] blockLocations = next.getBlockLocations();
			//[0,14978,hadoop02,hadoop04]
			//[0起始的偏移量,134217728  当前块的实际大小,hadoop04,hadoop02, 副本存放的位置   --文件的第一个块
			//134217728,80509869,hadoop04,hadoop02]  第二个块
			System.out.println(Arrays.toString(blockLocations));
			//循环遍历每一个文件的每一个数据块
			for(BlockLocation bl:blockLocations){
				System.out.print("这个块的起始偏移量"+bl.getOffset()+"\t");
				System.out.print("这个块的长度"+bl.getLength()+"\t");
				//这个是同一个数据块的所有副本存放的位置
				String[] hosts = bl.getHosts();
				System.out.println("这个数据块的副本存放位置"+Arrays.toString(hosts));
			}
		}*/
		
		//2.查看指定目录的文件或文件夹的状态信息
		//FileStatus  文件或文件夹的状态对象  封装的文件或文件夹的  用户  组   长度  路径
		FileStatus[] listStatus = fs.listStatus(new Path("/"));
		for(FileStatus fss:listStatus){
			System.out.println("=======================");
			System.out.println(fss.getPath());
			System.out.println(fss.getBlockSize());
			System.out.println(fss.getLen());
			System.out.println(fss.getReplication());
			
		}
		fs.close();
	}

}
