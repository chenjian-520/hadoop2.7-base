package com.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class testCrc {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		Configuration conf=new Configuration();
		FileSystem fs=FileSystem.get(new URI("hdfs://hadoop01:9000"), conf,"hadoop");
		Path src=new Path("/testcrc");
		Path dst=new Path("D:\\test04");
		/**
		 * �ļ����ع����� ֻҪ��һ����û���𻵵ĸ���  ������û���𻵵Ŀ�  crcУ���ǿ�ͨ����
		 * 
		 * crcУ���ʱ��   У�������ֻ��ԭʼ�ļ���ƫ�����ڵ�����  ֻҪ�ⲿ������û�з����仯  У��ͨ��
		 * �ⲿ������  �����仯  У�鲻ͨ����
		 * 
		 */
		fs.copyToLocalFile(src, dst);
		fs.close();
	}

}
