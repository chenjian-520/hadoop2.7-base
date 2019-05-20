package com.ghgj.combiner;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
/**
 * ����ͳ��
 */
import org.apache.hadoop.mapreduce.Reducer;
/**
 * reduce ��������Դ��map
 * @author ASUS
 *KEYIN, �����key������ ����ָ����map���key����  Text
 *VALUEIN, �����value������  ����ָ����map�����value������  IntWretable
 *���
 *KEYOUT, �����key 	����ָ�ĵ��ʵ�����  Text
 *VALUEOUT �����value������ 	����ָ���ǵ��ʵ��ܴ��� IntWritable
 */
public class WordCountrReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	/**
	 * ��reduce�˵����� ���Ѿ��ֺ��������
	 * Ĭ������� ����map�����key���з��� ��map�����key��ͬ�ķ�Ϊһ��
	 * Text key		ÿһ������ͬ��key
	 * Iterable<IntWritable> value  ÿһ���е����е�valueֵ ��װ��һ����������
	 * Context context �����Ķ��� ���ڴ���� д����hdfs��
	 * 
	 * reduce����Ƶ�ʣ�
	 *   һ�����һ��  ÿ��ֻͳ��һ���������ս��
	 */
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context) 
		throws IOException, InterruptedException {
		//ѭ������values ���
		int sum=0;
		for(IntWritable v : values){
			//IntWritable --- int  ��ֵ���� get ��hadoop�е�����ת��Ϊjava�е�����
			sum+=v.get();
		}
		//д�����
		IntWritable rv = new IntWritable(sum);
		context.write(key, rv);
	}
	
}
