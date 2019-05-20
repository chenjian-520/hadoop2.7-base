package com.ghgj.cn.defineClass;

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


public class FlowCount {
	//mapper
	static class MyMapper extends Mapper<LongWritable, Text, Text, FlowBean>{
		Text mk = new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context) 
					throws IOException ,InterruptedException {
			String[] datas = value.toString().split("\t");
			//��ȡ��Ҫ������  ���з�װ����
			mk.set(datas[1]);
			//��װfb
			int upFlow=Integer.parseInt(datas[datas.length-3].trim());
			int downFlow=Integer.parseInt(datas[datas.length-2].trim());
			FlowBean fb = new FlowBean(upFlow,downFlow);
			//����
			context.write(mk, fb);
		};
	}
	
	//reducer
	//key---�ֻ���  value---fb
	static class MyReducer extends Reducer<Text, FlowBean, Text, FlowBean>{
		@Override
		protected void reduce(Text key, Iterable<FlowBean> values,Context context)
				throws IOException, InterruptedException {
			//��ͬ���ֻ���  һ����ȫ��������   ѭ������  values�Ϳ�����
			int sum_upFlow=0;
			int sum_downFlow=0;
			for(FlowBean fb : values){
				sum_upFlow+=fb.getUpFlow();
				sum_downFlow+=fb.getDownFlow();
			}
			//��װ����
			FlowBean res_fb = new FlowBean(sum_upFlow,sum_downFlow);
			context.write(key, res_fb);
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
					job.setJarByClass(FlowCount.class);
					
					//����job��mapper����
					job.setMapperClass(MyMapper.class);
					
					//������Ƭ��С
					//FileInputFormat.setMaxInputSplitSize(job, size);
					//FileInputFormat.setMinInputSplitSize(job, size);
					
					//����job��reducer����
					job.setReducerClass(MyReducer.class);
					
					//����map���key  value������
					//ָ���˷��� ����Ϊʲô��Ҫ����һ��  ���͵���ҵ��Χ  �����ʱ����Ч ���е�ʱ���ͻ��Զ�����
					job.setMapOutputKeyClass(Text.class);
					job.setMapOutputValueClass(FlowBean.class);
					
					//����reduce�����k   v ���� һ�·������õ���Ĭ�ϵ��������
					job.setOutputKeyClass(Text.class);
					job.setOutputValueClass(FlowBean.class);
					
					//ָ����Ҫͳ�Ƶ��ļ�����·�� FileInputFormat �ļ�������
					Path inpath = new Path("hdfs://192.168.66.131:9000/in_phone01");
					FileInputFormat.addInputPath(job, inpath);
					
					//ָ�����Ŀ¼  ���·�����ܴ���  ����ᱨ��  Ĭ������Ǹ���ʽ����� ���Ŀ¼���� �п������ԭʼ���ݵĶ�ʧ
					Path outpath = new Path("hdfs://192.168.66.131:9000/phone01");
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

