package com.ghgj.cn.test;

import java.util.Arrays;

public class MergeSortFinal {
	public static void main(String[] args) {
		int[] arr={2,3,1,2,4,2,3,1};
		int[] newarr=new int[arr.length];
		chaiSort(arr, 0, arr.length-1, newarr);
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(newarr));
	}
	
	//拆
	public static void chaiSort(int[] arr,int first,int last,int[] newarr){
		if(first<last){
			//找中间点
			int mid=(first+last)/2;
			//拆左侧的数据集   //拆右侧的数据集
			chaiSort(arr, first, mid, newarr);
			//拆右侧的
			chaiSort(arr, mid+1, last, newarr);
			
			//并
			merge(arr, first, mid, last, newarr);
		}
	}
	
	
	//并   参数
	/**
	 * newarr 长度  ===  原来的arr的长度
	 * @param arr
	 * @param first
	 * @param mid
	 * @param last
	 * @param newarr
	 */
	
	public static void merge(int[] arr,int first,int mid,int last,int[] newarr){
		//第一个数据集的起始下标
		int m=first;
		int x=mid;//第一个数据集的终止的下标
		//第二个数据集的起始下标
		int n=mid+1;
		int y=last;//第二个数据集的终止下标
		//新数组的  起始下标
		int index=0;
		while(m<=x&&n<=y){
			if(arr[m]<=arr[n]){//第一个数据集的数据小
				newarr[index++]=arr[m++];
			}else{
				newarr[index++]=arr[n++];
			}
		}
		
		//如果   第一个数据集
		while(m<=x){
			newarr[index++]=arr[m++];
		}
		while(n<=y){
			newarr[index++]=arr[n++];
		}
		for(int i=0;i<index;i++){
			arr[first+i]=newarr[i];
		}
		
		
		
	}

}
