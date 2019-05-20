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
 * ɾ��HDFS��Ⱥ�е����п��ļ� �Ϳ��ļ���
 * @param args
 * �жϸ�����Ŀ¼�Ƿ�Ϊ��
 * Ϊ�� 
 * 	ɾ��
 * ��Ϊ��
 * 	ѭ���������е��ļ���Ŀ¼
 * 	������ļ�ɾ��
 *  �����Ŀ¼ �ݹ�
 *  �ж�ԭ����Ŀ¼�Ƿ�Ϊ��  ��ɾ��
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
