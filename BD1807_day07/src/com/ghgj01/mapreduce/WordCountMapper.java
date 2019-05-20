package com.ghgj01.mapreduce;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
/**
 * ����ͳ��
 */
import org.apache.hadoop.mapreduce.Mapper;
/**
 * �������  map���� 
 * KEYIN, ����ļ�������  ����ָ����ÿһ�е���ʼƫ���� long
 * VALUEIN, �����value������ ����ָ����һ�е�����   ��ƫ����һһ��Ӧ�� String
 * �������������   ȡ�����Լ���ҵ��
 * KEYOUT, ����ļ�������  ����ָ��ÿһ������
 * VALUEOUT �����ֵ������  ����ָ��ÿһ�����ʳ��ֵĴ���
 * @author ASUS
 * 
 * ������������� ����ʹ��java��ԭ������
 * 	���л������ݳ־û� �� ���紫���ʱ�� ������Ҫ���л��ͷ����л���
 * 	java---ʵ��---Serializable
 * 	mapreduce����е����ڴ�����������ͱ����������л��ͷ����л�����
 * 		hadoop������java��ԭ����Serializable  ʵ�ֵ�֮���һ�����л��ͷ����л��Ľӿ� Writable ֻ������ݵ�ֵ�������л��ͷ����л�
 *  	ԭ��java�е����л��ͷ����л�̫��   ����
 *  	����һЩ���õ��������� hadoop������ʵ�ֺ���
 *  	int---IntWritable
 *  	long---LongWritable
 *  	String---Text
 *  	btye---ByteWritable
 *  	double---DoubleWritable
 *  	float---FloatWritable
 *  	boolean--BooleanWritable
 *  	null---NullWritable
 *  �Լ��������Ҫ���л��ͷ����л� ʵ�� Writable�ӿ�
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	/**
	 * ��дmap����
	 * LongWritable key�������key ����ָ����ÿһ�е�ƫ����  û��ʵ������ һ�еı�ʾ����
	 * Text value, �����value ����ָ����һ�е����� 
	 * Context context �����Ķ��� ���ڴ���  ����reduce��
	 * �����ĵ���Ƶ�ʣ�
	 * һ�е���һ��
	 * һ���ļ�10��  map��������10��
	 */
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		//����һ���� ���ж�ȡ ��MapReduce��ܰ������ˣ�  ÿһ�����ݽ����з�
		//ÿһ�е����ݽ����з�
		//text---String
		String line = value.toString();
		//�����з� 
		String[] words = line.split("\t");
		//ѭ������ÿһ�����ʽ���ͳ�� ֱ�ӷ��͵�reduce��  ���͵�ʱ�� k-v
		for(String w:words){
			//��String---Text
			Text mk = new Text(w);
			//int ---- IntWritable
			IntWritable mv = new IntWritable(1);
			//�����writeֱ��д�� ����һ�� �ͻ�д��һ��
			context.write(mk, mv);
		}
		
	}

	
}






