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
		//�һ�׼��
		int key = arr[left];
	//	int left = 0; //��ʼ��ѭ���±�
	//	int right = arr.length-1;//��ʼ��ѭ���±�
		while(left<right){
			//��������ѭ�� ѭ������ �󲻱� С����
			while(arr[right]>=key&&left<right){
				right--;
			}
			//����ѭ��֤��arr[right]<key ����
			arr[left] = arr[right];  //arr[right]=key
			//��������ѭ��
			while(arr[left]<=key&&left<right){
				left++;
			}
			//��ѭ��  ֤��arr[left]>key ����
			arr[right]=arr[left];
		}
		//�������ѭ�� ����left=right
		//���ֽ��λ�ø�ֵ
		arr[left]=key;
		return left;
	}
	
	public static void QuickSort(int[] arr,int left,int right){
		//����
		if(left>=right){
			return;
		}
		//�ҷֽ����±�
		int index = getIndex(arr, left, right);
		//���±�Ϊ��������  �Ҳ�ֱ����� �ݹ����
		//������������
		QuickSort(arr, left, index-1);
		//���Ҳ��������
		QuickSort(arr, index+1, right);
	}
	
}
