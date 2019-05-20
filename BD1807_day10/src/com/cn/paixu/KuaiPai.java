package com.cn.paixu;

import java.util.Arrays;

public class KuaiPai {
	public static void main(String[] args) {
		int[] arr = new int[]{2,7,1,2,8,1,3};
		System.out.println(Arrays.toString(arr));
		sort(arr, 0, arr.length-1);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void sort(int[] arr,int left,int right){
		if(left>=right){
			return;
		}
		int index = merge(arr, left, right);
		sort(arr, left, index-1);
		sort(arr, index+1, right);
	}
	
	public static int merge(int[] arr, int left,int right ){
		int key = arr[left];
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
		arr[left]=key;
		return left;
	}
	
}
