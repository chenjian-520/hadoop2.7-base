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
		//�ļ��ϴ�  ����---hdfs
		//fs.copyFromLocalFile(new Path("D:\\movies.dat"), new Path("/movie_tt"));
		//�ļ�����
		//fs.copyToLocalFile(new Path("/movie_tt"), new Path("D:\\movie01.avi"));
		//�����ļ���  hdfs dfs -mkdir    ���������ļ���
		//boolean isfinish=fs.mkdirs(new Path("/bd1807/aa/ss/ff"));
		//System.out.println(isfinish);
		
		//ɾ���ļ���  ���ļ�  ����ֵ   bool  ɾ��--true   ����--false
		//delete() �ȿ���ɾ���ļ�  Ҳ����ɾ���ļ��е�
		//System.out.println(fs.delete(new Path("/aa001")));
		//ɾ���ļ���
		//System.out.println(fs.delete(new Path("/bd1807")));
		/**
		 * ����1����Ҫɾ����·��  �ļ�   �ļ���
		 * ����2���Ƿ���Ҫ����ɾ��  true��Ҫ  false  --����Ҫ
		 */
		//fs.delete(new Path("/ss"), false);
		//fs.delete(new Path("/ss"),true);
		//�ж��ļ���/�ļ��Ƿ����  �������  �򷵻�true   ����--false
		//boolean ise=fs.exists(new Path("/bd1807"));   //mkdir/delete
		//System.out.println(ise);
		/*if(fs.exists(new Path("/movie_tt"))){
			fs.delete(new Path("/movie_tt"),false);
		}*/
		//�鿴�ļ�����Ϣ
		//1.�鿴�ļ�����Ϣ  ָ��·���µ��ļ���Ϣ  ����1--��Ҫ�鿴��·��  ����2--�Ƿ���  ls -R
		/*RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), false);
		//������ ����   hasNext   next()
		//LocatedFileStatus  �ļ�״̬����   ��װ���ļ�״̬ -- �ļ���·�� �ļ��Ĵ�С  �ļ����û�  ��  �ļ��ĸ���
		while(listFiles.hasNext()){
			System.out.println("------------------------------");
			//next����  ��ȡ�����ļ���״̬��Ϣ  һ��next��������һ���ļ�
			LocatedFileStatus next = listFiles.next();
			System.out.println(next.getPath());
			System.out.println(next.getBlockSize());//��ȡ�������ļ��ķֿ��С   128M
			System.out.println(next.getLen());//��ȡ�ļ�����ʵ��С  ���ʵ�ʴ�С
			System.out.println(next.getReplication());
			//��ȡ�����ļ����п���Ϣ  ÿһ���ļ����п���Ϣ
			//BlockLocation   ���ݿ�ķ�װ����   �������ݿ�Ĵ洢λ��  ���ݿ�Ĵ�С  ���ݿ�������û�  ��
			BlockLocation[] blockLocations = next.getBlockLocations();
			//[0,14978,hadoop02,hadoop04]
			//[0��ʼ��ƫ����,134217728  ��ǰ���ʵ�ʴ�С,hadoop04,hadoop02, ������ŵ�λ��   --�ļ��ĵ�һ����
			//134217728,80509869,hadoop04,hadoop02]  �ڶ�����
			System.out.println(Arrays.toString(blockLocations));
			//ѭ������ÿһ���ļ���ÿһ�����ݿ�
			for(BlockLocation bl:blockLocations){
				System.out.print("��������ʼƫ����"+bl.getOffset()+"\t");
				System.out.print("�����ĳ���"+bl.getLength()+"\t");
				//�����ͬһ�����ݿ�����и�����ŵ�λ��
				String[] hosts = bl.getHosts();
				System.out.println("������ݿ�ĸ������λ��"+Arrays.toString(hosts));
			}
		}*/
		
		//2.�鿴ָ��Ŀ¼���ļ����ļ��е�״̬��Ϣ
		//FileStatus  �ļ����ļ��е�״̬����  ��װ���ļ����ļ��е�  �û�  ��   ����  ·��
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