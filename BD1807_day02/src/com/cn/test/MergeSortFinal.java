package com.cn.test;

import java.util.Arrays;

public class MergeSortFinal {

	public static void main(String[] args) {
		int[] arr1={1,2,3,5,685,81,5,7,9,15,35};
		int[] arr2=new int[arr1.length];
		chaiSort(arr1, 0, arr1.length-1, arr2);
		System.out.println(Arrays.toString(arr2));
		System.out.println(Arrays.toString(arr1));
		
	}
	//拆
	public static void chaiSort(int[] arr, int first, int last, int[] newarr){
		if(first<last){
			//找中间点
			int mid = (first+last)/2;
			//拆左侧的数据集
			chaiSort(arr, first, mid, newarr);
			//拆右侧的数据集
			chaiSort(arr, mid+1, last, newarr);
			
			merge(arr, first, mid, last, newarr);
		}
	}
	
	//并 参数
	public static int[] merge(int[] arr, int first, int mid, int last, int[] newarr){
		//第一个数据集的起始下标
		int m=first;
		int x=mid;//第一个数据集的终止下标
		//第二个数据集的起始下标
		int n=mid+1;
		int y=last;//第二个数据集的终止下标
		//新数组的起始下标
		int index=0;
		while(m<=x&&n<=y){
			if(arr[m]<=arr[n]){
				newarr[index++]=arr[m++];
			}else{
				newarr[index++]=arr[n++];
			}
		}
		while(m<=x){
			newarr[index++]=arr[m++];
		}
		while(n<=y){
			newarr[index++]=arr[n++];
		}
		for(int i=0;i<index;i++){
			arr[first+i]=newarr[i];
		}
		return newarr;
	}
	
}
