package com.ghgj.cn.homework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class MianShiTi01 {
	static class MyMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text mk=new Text();
		Text mv=new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] datas = value.toString().split("\t");
			//�жϵڶ����ֶ��Ƿ�Ϊ0
			if("0".equals(datas[1])){//��Ŀ¼  �Ҷ���
				//1        0        �ҵ�
				mk.set(datas[0]);//�Լ���id
				mv.set(datas[1]+"\t"+datas[2]);
				context.write(mk, mv);
			}else{//���Ǹ�Ŀ¼  �Ұְ�
				//4        1        ϴ�»�
				mk.set(datas[1]);//���͸�id
				mv.set(datas[0]+"\t"+datas[2]);
				context.write(mk, mv);
			}
		}
	}
	static class MyReducer extends Reducer<Text, Text, Text, Text>{
		Text rk=new Text();
		Text rv=new Text();
		/**
		 * ��Ŀ¼
		 * key=1       value�� 0        �ҵ�
		 * ��Ŀ¼��
		 * 4        1        ϴ�»�
5        1        ����
		 * key=1   value:4   ϴ�»�           5   ����
		 */
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String parent="";
			List<String> childDir=new ArrayList<String>();
			for(Text v:values){
				String tmp = v.toString();
				if(tmp.startsWith("0")){//��Ŀ¼
					parent=tmp;
				}else{//��Ŀ¼
					childDir.add(tmp);
				}
			}
			//ƴ��
			//û�л�ȡ��Ŀ¼  parent=""  �������  �ȴ��Լ��ĸ�Ŀ¼ƴ�ӳ���
			if("".equals(parent)&&childDir.size()!=0){//ԭ�����
				//key=14    value:15             ������
				// 15        14        ������
				for(int i=0;i<childDir.size();i++){
				String[] temp01 = childDir.get(i).split("\t");
				rk.set(temp01[0]);
				rv.set(key.toString()+"\t"+temp01[1]);
				context.write(rk, rv);
				}
			}else if(childDir.size()==0){////û����Ŀ¼
				//10			0		�ҵ�-ϴ�»�-����
				//key=10   value:0		�ҵ�-ϴ�»�-����
				rv.set(parent);
				context.write(key, rv);
			}else{
				/* ��Ŀ¼
				 * key=1       value�� 0        �ҵ�
				 * ��Ŀ¼��
				 * 4        1        ϴ�»�
		5        1        ����
				 * key=1   value:4   ϴ�»�           5   ����
				 *
				 */
				//ѭ��������Ŀ¼  �͸�Ŀ¼ƴ��
				for(String c:childDir){
					//c=4   ϴ�»�     //p= 0        �ҵ�  //res=4   0   �ҵ�-ϴ�»�
					String[] temp02 = c.split("\t");
					String[] temp03 = parent.split("\t");
					rk.set(temp02[0]);
					rv.set(temp03[0]+"\t"+temp03[1]+"-"+temp02[1]);
					context.write(rk, rv);
				}
				
				 
			}
			
			
			
			
			
			
			
			//����
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		int count=0;
		while(true){
			count++;
			System.setProperty("HADOOP_USER_NAME", "hadoop");
			Configuration conf=new Configuration();
			conf.set("fs.defaultFS", "hdfs://hadoop01:9000");
			Job job=Job.getInstance(conf);
			job.setJarByClass(MianShiTi01.class);
			
			
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			if(count==1){
				FileInputFormat.addInputPath(job, new Path("hdfs://hadoop01:9000/mianshiin"));
			}else{
				FileInputFormat.addInputPath(job, new Path("hdfs://hadoop01:9000/mianshi_out0"+(count-1)));
			}
			
			//��Ҫ����          ��ɹ���־�ļ�
			FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop01:9000/mianshi_out0"+count));
			//�ύ����
			job.waitForCompletion(true);
			if(count==3){
				break;
			}
		}
		
	}

}
