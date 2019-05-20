package com.ghgj.cn.student02;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Student02 implements WritableComparable<Student02> {
	private String name;
	private int count;
	private double sum;
	
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(name);
		out.writeInt(count);
		out.writeDouble(sum);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.name = in.readUTF();
		this.count = in.readInt();
		this.sum = in.readDouble();
	}
	@Override
	public int compareTo(Student02 o) {
		// TODO Auto-generated method stub
		int temp = (int) (o.getSum()*10-this.getSum()*10);
		if(temp==0){
			temp=this.count-o.count;
		}
		return temp;
	}
	
	@Override
	public String toString() {
		return  name + "\t" + count + "\t" + sum ;
	}

	
	public Student02(String name, int count, double sum) {
		super();
		this.name = name;
		this.count = count;
		this.sum = sum;
	}


	public Student02() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}

	
	
}
