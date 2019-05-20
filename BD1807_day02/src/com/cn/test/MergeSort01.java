package com.cn.test;

import java.util.Arrays;

public class MergeSort01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr1={1,2,3,5,7,9,15,35};
		int[] arr2={2,5,8,11,25,35,65};
		int[] res=bingSort(arr1, arr2);
		System.out.println(Arrays.toString(res));
	}
	
	public static int[] bingSort(int[] arr1 , int [] arr2){
		//创建新数组
		int[] newarr = new int[arr1.length+arr2.length];
		//定义三个下标
		int m=0,n=0;
		int index=0;
		while(m<arr1.length && n<arr2.length){
			//进行判断 放在新数组中
			//arr1中的元素小于 arr2中的元素
			if(arr1[m]<=arr2[n]){
				newarr[index++]=arr1[m++];
			}else{
				newarr[index++]=arr2[n++];
			}	
		}
		//有可能剩下arr1 或 arr2
		for(int i=m;i<arr1.length;i++){
			newarr[index++]=arr1[i];
		}
		while(n<arr2.length){
			newarr[index++]=arr2[n++];
		}
		
		return newarr;
		
	}
	
}
