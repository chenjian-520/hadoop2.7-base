package com.ghgj.cn.defineSort;


import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




public class FlowSort {
	static class MyMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable>{
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, FlowBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			//��װflowbean fason
			FlowBean fb = new FlowBean(datas[0].trim(),
					Integer.parseInt(datas[1].trim()),
					Integer.parseInt(datas[2].trim()),
					Integer.parseInt(datas[3].trim()));
			//���� 
			context.write(fb, NullWritable.get());
		}
	} 
	
	static class MyReducer extends Reducer<FlowBean, NullWritable, FlowBean, NullWritable>{
		@Override
		protected void reduce(FlowBean key, Iterable<NullWritable> values,
				Reducer<FlowBean, NullWritable, FlowBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			//������
			context.write(key, NullWritable.get());
		}
	}
	
	public static void main(String[] args) {
		//��mapper  reducer �����һ����װ ��װΪһ������ ---job
		System.setProperty("HADOOP_USER_NAME", "chen");
			//���������ļ�
			Configuration conf = new Configuration();
			//����һ��job ����һ��job����
			try {
				Job job = Job.getInstance(conf);
				//�������job
				//��ֵ����job�����������
				job.setJarByClass(FlowSort.class);
				
				//����job��mapper����
				job.setMapperClass(MyMapper.class);
				
				//������Ƭ��С
				//FileInputFormat.setMaxInputSplitSize(job, size);
				//FileInputFormat.setMinInputSplitSize(job, size);
				
				//����job��reducer����
				job.setReducerClass(MyReducer.class);
				
				//����map���key  value������
				//ָ���˷��� ����Ϊʲô��Ҫ����һ��  ���͵���ҵ��Χ  �����ʱ����Ч ���е�ʱ���ͻ��Զ�����
				job.setMapOutputKeyClass(FlowBean.class);
				job.setMapOutputValueClass(NullWritable.class);
				
				//����reduce�����k   v ���� һ�·������õ���Ĭ�ϵ��������
				job.setOutputKeyClass(FlowBean.class);
				job.setOutputValueClass(NullWritable.class);
				
				//ָ����Ҫͳ�Ƶ��ļ�����·�� FileInputFormat �ļ�������
				Path inpath = new Path("hdfs://192.168.66.131:9000/phone01");
				FileInputFormat.addInputPath(job, inpath);
				
				//ָ�����Ŀ¼  ���·�����ܴ���  ����ᱨ��  Ĭ������Ǹ���ʽ����� ���Ŀ¼���� �п������ԭʼ���ݵĶ�ʧ
				Path outpath = new Path("hdfs://192.168.66.131:9000/phone01_sort");
				FileOutputFormat.setOutputPath(job, outpath);
				
				//�ύjob ִ����һ���ʱ��
				//job.submit(); ���ܴ�ӡ��־
				job.waitForCompletion(true);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
