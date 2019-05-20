package com.cn.paixu;

import java.util.Arrays;

public class GuiBing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr1={1,2,3,5,685,81,5,7,9,15,35};
		int[] arr2=new int[arr1.length];
		caifeng(arr1, 0, arr1.length-1, arr2);
		System.out.println(Arrays.toString(arr2));
		System.out.println(Arrays.toString(arr1));
	}
	public static void caifeng(int[] arr,int left,int right,int[] newarr){
		if(left<right){
			int mid=(left+right)/2;
			caifeng(arr, left, mid, newarr);
			caifeng(arr, mid+1, right, newarr);
			hebing(arr, left, mid, right, newarr);
		}
	}
	
	public static void hebing(int[] arr,int left,int mid,int right,int[] newarr){
		int m = left;
		int x = mid;
		
		int n = mid+1;
		int y = right;
		
		int index = 0;
		while(m<=x&&n<=y){
			if(arr[m]>=arr[n]){
				newarr[index++]=arr[n++];
			}else{
				newarr[index++]=arr[m++];
			}
		}
		while(m<=x){
			newarr[index++]=arr[m++];
		}
		while(n<=y){
			newarr[index++]=arr[n++];
		}
		for(int i=0;i<index;i++){
			arr[left+i]=newarr[i];
		}
	}
}
