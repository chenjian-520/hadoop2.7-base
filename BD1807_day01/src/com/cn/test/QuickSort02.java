package com.cn.test;

public class QuickSort02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static int getIndex(int[] arr,int left,int right){
		int key=arr[left];
		while(left<right){
			while(arr[right]>=key&&left<right){
				right--;
			}
			arr[left]=arr[right];
			while(arr[left]<=key&&left<right){
				left++;
			}
			arr[right]=arr[left];
		}
		arr[left] = key;
		return left;
	}
	
	public static void quick(int[] arr,int left,int right){
		if(left>=right){
			return;
		}
		int index = getIndex(arr, left, right);
		
		quick(arr, left, index-1);
		
		quick(arr, index+1, right);
	}
	
}
