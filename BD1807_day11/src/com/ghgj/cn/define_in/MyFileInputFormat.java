package com.ghgj.cn.define_in;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * �Զ����ļ�������
 * @author ASUS
 * ���ͣ��ļ���ȡ��ɵ�key  value �ķ��� ��ȡ���---Mapper
 * һ�ζ�ȡһ���ļ�
 * �ļ�����
 */
public class MyFileInputFormat extends FileInputFormat<NullWritable, Text>{
	@Override
	public RecordReader<NullWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		MyRecordReader mrr = new MyRecordReader();
		mrr.initialize(split, context);
		return mrr;
	}
}
