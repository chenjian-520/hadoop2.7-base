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
	//��
	public static void chaiSort(int[] arr, int first, int last, int[] newarr){
		if(first<last){
			//���м��
			int mid = (first+last)/2;
			//���������ݼ�
			chaiSort(arr, first, mid, newarr);
			//���Ҳ�����ݼ�
			chaiSort(arr, mid+1, last, newarr);
			
			merge(arr, first, mid, last, newarr);
		}
	}
	
	//�� ����
	public static int[] merge(int[] arr, int first, int mid, int last, int[] newarr){
		//��һ�����ݼ�����ʼ�±�
		int m=first;
		int x=mid;//��һ�����ݼ�����ֹ�±�
		//�ڶ������ݼ�����ʼ�±�
		int n=mid+1;
		int y=last;//�ڶ������ݼ�����ֹ�±�
		//���������ʼ�±�
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
