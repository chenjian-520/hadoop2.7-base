package com.cn.test;

public class Erfenfa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int srcArray[] = {3,5,11,17,21,23,28,30,32,50,64,78,81,95,101}; 
		System.out.println(fena(srcArray,0,srcArray.length, 81));  
	}
	public static int fenfa(int[] arr,int key){
		int mid = arr.length/2 ;
		if(arr[mid]==key){
			return mid;
		}
		int start = 0;
		int end = arr.length-1;
		while(start<=end){
			mid = (end-start)/2+start;
			if(arr[mid]>key){
				end = mid-1;
			}else if(arr[mid]<key){
				start = mid+1;
			}else{
				return mid;
			}

		}	
		return -1;	
	}
	public static int fena(int[] arr ,int start,int end ,int key){
		int mid = (end-start)/2+start;
		if(arr[mid]==key){
			return mid;
		}
		if(start<=end){
			if(arr[mid]>key){
				return fena(arr, start, mid-1, key);
			}else if(arr[mid]<key){
				return fena(arr, mid+1, end, key);
			}	
		}
		return -1;
	}
}
