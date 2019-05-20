package com.ghgj.workHuFen;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class GongTongFriend01 {

	static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
		
		   Text mk = new Text();
	       Text mv = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			  //A    F,I,O,K,G,D,C,H,B ����˼��F,I,O,K,G,D,C,H,B�Ĺ�ͬ����ΪA,�ڶ���ִ����ȡ��������,AΪvalue��������Ϊkey
            String [] reads = value.toString().trim().split("\t");
            //AΪvalue
            String vv = reads[0];
            mv.set(vv);
            //���������Ѷԣ�Ϊ�˷�ֹ�ظ�����Ҫ�Ժ��ѽ�������
            String [] friends = reads[1].split(",");
            Arrays.sort(friends);
            //ʹ������ѭ����Ѱ��A-B�Ⱥ��Ѷ����
            for(int i = 0; i < friends.length - 1; i++){
                for(int j = i + 1; j < friends.length; j++ ){
                    String kk = friends[i] + "-" + friends[j];
                    mk.set(kk);
                    context.write(mk, mv);
                }
            }
		}
	}
	static class MyReducer extends Reducer<Text, Text, Text, Text> {
	     Text mv = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			 //���Ѷ��������
            StringBuilder sb = new StringBuilder();
            for(Text text : values){
                sb.append(text.toString()).append(",");
            }
            String vv = sb.substring(0, sb.length()-1);
            mv.set(vv);
            context.write(key, mv);
        }
	}
	
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "chen");
		Configuration conf = new Configuration();
		try {
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(GongTongFriend.class);
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			Path inpath = new Path("hdfs://192.168.66.131:9000/work_hufen_out1");
			FileInputFormat.addInputPath(job, inpath);
			Path outpath = new Path("hdfs://192.168.66.131:9000/work_hufen_out2");
			FileOutputFormat.setOutputPath(job, outpath);
			
			job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
