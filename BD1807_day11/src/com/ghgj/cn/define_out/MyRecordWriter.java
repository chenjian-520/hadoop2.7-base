package com.ghgj.cn.define_out;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class MyRecordWriter extends RecordWriter<Text, DoubleWritable> {
	private FileSystem  fs;
	Path path1=new Path("hdfs://hadoop01:9000/score_out/jige/jige_stu");
	Path path2=new Path("hdfs://hadoop01:9000/score_out/bujige/bujige_stu");

	FSDataOutputStream out1=null;
	FSDataOutputStream out2=null;
	public MyRecordWriter(FileSystem fs) throws IOException {
		this.fs=fs;
		out1 = this.fs.create(path1);
		out2= this.fs.create(path2);
	}

	//文件写出的方法
	/**
	 * 获取hdfs的输出流
	 * 	fs.create()  FileSyatem.get(conf)
	 */
	@Override
	public void write(Text key, DoubleWritable value) throws IOException, InterruptedException {
		//获取分数
		double score = value.get();
		if(score>=60){
			out1.write((key.toString()+"---"+score+"\n").getBytes());
		}else{
			out2.write((key.toString()+"---"+score+"\n").getBytes());
		}

	}

	//关闭流的方法
	@Override
	public void close(TaskAttemptContext context) throws IOException, InterruptedException {
		fs.close();
		out1.close();
		out2.close();

	}

}
