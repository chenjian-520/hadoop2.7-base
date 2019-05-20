package com.ghgj.cn.define_out;

import org.apache.hadoop.io.Text;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * 
 * @author Administrator
 *���ͣ�reduce�������key  value
 */
public class MyFileOutputFormat extends FileOutputFormat<Text, DoubleWritable>{
	
	/**
	 * �����������Ķ���
	 */

	@Override
	public RecordWriter<Text, DoubleWritable> getRecordWriter(TaskAttemptContext job)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FileSystem fs=FileSystem.get(job.getConfiguration());
		return new MyRecordWriter(fs);
	}

}
