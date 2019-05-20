package com.ghgj.combiner;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * maptask --- combiner---reducetask
 *ǰ������Ӧ��maptask�����
 *��������Ӧreducetask������
 *ǰ�������͵�����===����������
 */
public class MyCombiner extends Reducer<Text, IntWritable, Text, IntWritable>{
	/**
	 * ����Ƶ�� �� һ�����һ��
	 * һ�飺��ͬ��keyΪһ��
	 * �����combiner-----��Ӧ�Ľ�����һ��maptask������--һ��������
	 * reducer��Ӧ�������е�maptask����
	 */
	IntWritable rv = new IntWritable();
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		//ѭ������values ���
		int sum=0;
		for(IntWritable v : values){
			//IntWritable --- int  ��ֵ���� get ��hadoop�е�����ת��Ϊjava�е�����
			sum+=v.get();
		}
		//д�����
		rv.set(sum);
		context.write(key, rv);
		
	}
}
