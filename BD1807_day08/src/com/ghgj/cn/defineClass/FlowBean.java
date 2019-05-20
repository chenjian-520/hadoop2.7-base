package com.ghgj.cn.defineClass;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class FlowBean implements Writable{
	//上行流量
	private int upFlow;
	//下行流量
	private int downFlow;
	//总流量
	private int sumFlow;
	
	//序列化方法
	@Override
	public void write(DataOutput out) throws IOException {
		// 通过参数的输出流 写出   字符串的序列化  writeUTF
		out.writeInt(upFlow);
		out.writeInt(downFlow);
		out.writeInt(sumFlow);
	}
	//反序列化方法
	//反序列化的顺序  一定要和 序列化的顺序一样
	@Override
	public void readFields(DataInput in) throws IOException {
		// 从流中读取  转换过来  String---readUtf
		this.upFlow=in.readInt();
		this.downFlow=in.readInt();
		this.sumFlow=in.readInt();
	}
	
	@Override
	public String toString() {
		return upFlow + "\t" + downFlow + "\t" + sumFlow;
	}
	
	public FlowBean(int upFlow, int downFlow) {
		super();
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = upFlow+downFlow;
	}
	
	public FlowBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getUpFlow() {
		return upFlow;
	}
	public void setUpFlow(int upFlow) {
		this.upFlow = upFlow;
	}
	public int getDownFlow() {
		return downFlow;
	}
	public void setDownFlow(int downFlow) {
		this.downFlow = downFlow;
	}
	public int getSumFlow() {
		return sumFlow;
	}
	public void setSumFlow(int sumFlow) {
		this.sumFlow = sumFlow;
	}


}
