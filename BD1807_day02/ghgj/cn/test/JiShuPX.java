package com.ghgj.cn.test;

import java.util.Arrays;

public class JiShuPX {
	public static void main(String[] args) {
		int[] arr={1,4,7,3,2,4};
		int[] res=jspx(arr);
		System.out.println(Arrays.toString(res));
	}
	
	
	public static int[] jspx(int[] arr){
		//求  最大值  和最小值
		int max=arr[0];
		int min=arr[0];
		for(int a:arr){
			if(max<a){
				max=a;
			}
			if(min>a){
				min=a;
			}
		}
		int[] pxarr=new int[max-min+1];
		//循环遍历  旧数组进行计数排序
		for(int a:arr){
			pxarr[a-min]+=1;
		}
		//遍历输出
		//创建最终数组
		int[] result=new int[arr.length];
		//记录最终数组的下标
		int index=0;
		//先循环 每一个元素  计数排序器的下标中
		for(int i=0;i<pxarr.length;i++){
			//循环出现的次数
			for(int j=0;j<pxarr[i];j++){
				result[index++]=i+min;
			}
		}
		return result;
	}

}
