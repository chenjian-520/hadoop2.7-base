package com.ghgj.reducetask;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowPartition {
	
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text mk = new Text();
		Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			mk.set(datas[0]);
			mv.set(datas[1]+"\t"+datas[2]+"\t"+datas[3]);
			context.write(mk, mv);		
		}
	}
	
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			for(Text v:values){
				context.write(key, v);
			}
			
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
						job.setJarByClass(FlowPartition.class);
						
						//����job��mapper����
						job.setMapperClass(MyMapper.class);
						
						//������Ƭ��С
						//FileInputFormat.setMaxInputSplitSize(job, size);
						//FileInputFormat.setMinInputSplitSize(job, size);
						
						//����job��reducer����
						job.setReducerClass(MyReducer.class);
						
						
						//ָ���Զ������
						job.setPartitionerClass(Mypartitioner.class);
						//ָ��reducetask����
						job.setNumReduceTasks(3);
						
						//����map���key  value������
						//ָ���˷��� ����Ϊʲô��Ҫ����һ��  ���͵���ҵ��Χ  �����ʱ����Ч ���е�ʱ���ͻ��Զ�����
						job.setMapOutputKeyClass(Text.class);
						job.setMapOutputValueClass(Text.class);
						
						//����reduce�����k   v ���� һ�·������õ���Ĭ�ϵ��������
						job.setOutputKeyClass(Text.class);
						job.setOutputValueClass(Text.class);
						
						//ָ����Ҫͳ�Ƶ��ļ�����·�� FileInputFormat �ļ�������
						Path inpath = new Path("hdfs://192.168.66.131:9000/phone01_sort");
						FileInputFormat.addInputPath(job, inpath);
						
						//ָ�����Ŀ¼  ���·�����ܴ���  ����ᱨ��  Ĭ������Ǹ���ʽ����� ���Ŀ¼���� �п������ԭʼ���ݵĶ�ʧ
						Path outpath = new Path("hdfs://192.168.66.131:9000/phone01_Reducetask01");
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
