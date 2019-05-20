package com.ghgj01.testmapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	//һ�е���һ��
	Text mk=new Text();
	IntWritable mv=new IntWritable();
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		//��ȡÿһ������
		String line = value.toString();
		//�з�
		String[] datas = line.split("\t");
		//ѭ������  ����
		for(String dt:datas){
			
			//����ֵ
			mk.set("a");
			mv.set(Integer.parseInt(dt));
			context.write(mk, mv);
		}
	}

}
