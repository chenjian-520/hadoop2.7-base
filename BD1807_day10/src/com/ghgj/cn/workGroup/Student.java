package com.ghgj.cn.workGroup;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Student implements WritableComparable<Student> {
	
	private String ban;
	private String count;
	private String name;
	private int sum;
	
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeUTF(ban);
		out.writeUTF(count);
		out.writeUTF(name);
		out.writeInt(sum);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.ban=in.readUTF();
		this.count=in.readUTF();
		this.name=in.readUTF();
		this.sum=in.readInt();
	}

	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		int temp=this.ban.compareTo(o.ban);
		if(temp==0){
			temp=o.sum-this.sum;
			if(temp==0){
				temp=o.name.compareTo(this.name);
			}
		}
		return temp;
	}
	
	@Override
	public String toString() {
		return  ban + "\t" + count + "\t" + name + "\t" + sum ;
	}

	public Student(String ban, String count, String name, int sum) {
		super();
		this.ban = ban;
		this.count = count;
		this.name = name;
		this.sum = sum;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}


	
}
