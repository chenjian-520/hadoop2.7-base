package com.ghgj.cn.define_in;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class MyRecordReader extends RecordReader<NullWritable, Text> {

	Text map_value = new Text();
	//�жϵ�ǰ�ļ��Ƿ��пζ�ȡ��
	boolean isReader=false;
	//��ʼ������  ������setup����  ��һЩ����  �������ĳ�ʼ��
	FileSystem fs=null;

	FSDataInputStream in = null;
	long length = 0;
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//��ȡfs����
		 fs=FileSystem.get(context.getConfiguration());
		 //��ȡ��
		 FileSplit fss = (FileSplit)split;
		 Path path = fss.getPath();
		 in = fs.open(path);
		 length = fss.getLength();
	}
	/**
	 * �����ļ��Ķ�ȡ
	 */
	//�ж��Ƿ�����һ��
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		/**
		 * �����ļ���ȡ
		 */
		if(!isReader){
			//��ʼ���ж�ȡ
			/**
			 * ����1����ȡ���ֽ�����
			 * ����2����ʼ��ȡ��ƫ����
			 * ����3����ȡ�ĳ���
			 */
			byte[] b = new byte[(int)length];
			//�����ȡ�ĳ���   ������Ƭ�ĳ���
			in.readFully(b, 0, (int)length);
			//��ȡ��   ==  value
			map_value.set(b);
			isReader = true;
			return isReader;
		}
		
		return false;
	}
	//��ȡ��mapper��key
	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return NullWritable.get();
	}
	//��ȡ�����յ�value
	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		return map_value;
	}
	//��ȡ���ȵķ���
	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return isReader?1.0f:0.0f;
	}
	//�رյķ���
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		in.close();
		fs.close();
	}

}
