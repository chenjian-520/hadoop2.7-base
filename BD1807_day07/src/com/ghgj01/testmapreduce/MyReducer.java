package com.ghgj01.testmapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * reduce���
 * 	key�����ֵ/��Сֵ/ƽ��ֵ
 * value��ֵ
 * @author Administrator
 *
 */
public class MyReducer extends Reducer<Text, IntWritable, Text, DoubleWritable>{
	Text rk=new Text();
	DoubleWritable rv=new DoubleWritable();
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		//ѭ������values
		int max=0;
		int min=100000;
		int sum=0;
		int count=0;
		for(IntWritable v:values){
			int data=v.get();
			count++;
			sum+=data;
			//���ֵ
			if(max<data){
				max=data;
			}
			if(min>data){
				min=data;
			}
		}
		double avg=(double)sum/count;
		//���
		rk.set("���ֵ��");
		rv.set(max);
		//context.write(rk, rv);   ���Ե��ö��  ����һ�� д��һ������
		context.write(rk, rv);
		
		rk.set("��Сֵ��");
		rv.set(min);
		context.write(rk, rv);
		
		rk.set("ƽ��ֵ��");
		rv.set(avg);
		context.write(rk, rv);
	}
}
