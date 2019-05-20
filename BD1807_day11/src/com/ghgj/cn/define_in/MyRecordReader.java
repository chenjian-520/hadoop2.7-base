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
	//判断当前文件是否还有课读取的
	boolean isReader=false;
	//初始化方法  类似于setup方法  做一些属性  或者流的初始化
	FileSystem fs=null;

	FSDataInputStream in = null;
	long length = 0;
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//获取fs对象
		 fs=FileSystem.get(context.getConfiguration());
		 //获取流
		 FileSplit fss = (FileSplit)split;
		 Path path = fss.getPath();
		 in = fs.open(path);
		 length = fss.getLength();
	}
	/**
	 * 进行文件的读取
	 */
	//判断是否还有下一个
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		/**
		 * 进行文件读取
		 */
		if(!isReader){
			//开始进行读取
			/**
			 * 参数1：读取的字节数组
			 * 参数2：开始读取的偏移量
			 * 参数3：读取的长度
			 */
			byte[] b = new byte[(int)length];
			//这里读取的长度   整个切片的长度
			in.readFully(b, 0, (int)length);
			//读取的   ==  value
			map_value.set(b);
			isReader = true;
			return isReader;
		}
		
		return false;
	}
	//获取给mapper的key
	@Override
	public NullWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return NullWritable.get();
	}
	//读取的最终的value
	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		return map_value;
	}
	//获取进度的方法
	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return isReader?1.0f:0.0f;
	}
	//关闭的方法
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		in.close();
		fs.close();
	}

}
