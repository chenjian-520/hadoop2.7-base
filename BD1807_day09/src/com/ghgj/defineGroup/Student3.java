package com.ghgj.defineGroup;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 求每门课程的 平均分最高的前三
 * 排序： 平均分
 * 分组：课程
 * 定义排序规则
 * @author ASUS
 *
 */

public class Student3 implements WritableComparable<Student3> {
	private String name;
	private String count;
	private double sum;
	
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(name);
		out.writeUTF(count);
		out.writeDouble(sum);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.name = in.readUTF();
		this.count = in.readUTF();
		this.sum = in.readDouble();
	}
	@Override
	public int compareTo(Student3 o) {
		// TODO Auto-generated method stub
		int temp =this.name.compareTo(o.name);
		if(temp==0){
			double temp1=(o.sum-this.sum);
			if(temp1>0){
				temp=1;
			}else if(temp1<0){
				temp=-1;
			}else{
				temp=0;
			}
		}
		return temp;
	}
	
	@Override
	public String toString() {
		return  name + "\t" + count + "\t" + sum ;
	}

	
	public Student3(String name, String count, double sum) {
		super();
		this.name = name;
		this.count = count;
		this.sum = sum;
	}


	public Student3() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}

	
	
}
