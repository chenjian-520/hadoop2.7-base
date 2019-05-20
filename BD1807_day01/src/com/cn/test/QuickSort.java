package com.cn.test;

import java.util.Arrays;

public class QuickSort {
	public static void main(String[] args) {
		int[] arr = new int[]{2,7,1,2,8,1,3};
		System.out.println(Arrays.toString(arr));
		QuickSort(arr, 0, arr.length-1);
		System.out.println(Arrays.toString(arr));
		
	/*	caru(arr);
		System.out.println(Arrays.toString(arr));*/
	}


public static void caru(int[] arr){
	int i ,j;
	int temp;
	for(i=1;i<arr.length;i++){
		 temp = arr[i];
		 j=i-1;
		 while((j>=0)&&temp<arr[j]){
			 arr[j+1]=arr[j];
			 j--;
		 }
		 arr[j+1]=temp;
		
		}
	}

	public static int getIndex(int[] arr,int left , int right){
		//找基准点
		int key = arr[left];
	//	int left = 0; //初始左循环下标
	//	int right = arr.length-1;//初始右循环下标
		while(left<right){
			//从右向左循环 循环条件 大不变 小交换
			while(arr[right]>=key&&left<right){
				right--;
			}
			//出了循环证明arr[right]<key 交换
			arr[left] = arr[right];  //arr[right]=key
			//从左向右循环
			while(arr[left]<=key&&left<right){
				left++;
			}
			//出循环  证明arr[left]>key 交换
			arr[right]=arr[left];
		}
		//出了外层循环 等于left=right
		//给分界点位置赋值
		arr[left]=key;
		return left;
	}
	
	public static void QuickSort(int[] arr,int left,int right){
		//出口
		if(left>=right){
			return;
		}
		//找分界点的下标
		int index = getIndex(arr, left, right);
		//以下标为界进行左侧  右侧分别排序 递归调用
		//对左侧进行排序
		QuickSort(arr, left, index-1);
		//对右侧进行排序
		QuickSort(arr, index+1, right);
	}
	
}
