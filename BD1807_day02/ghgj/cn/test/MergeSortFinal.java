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
	
	//��
	public static void chaiSort(int[] arr,int first,int last,int[] newarr){
		if(first<last){
			//���м��
			int mid=(first+last)/2;
			//���������ݼ�   //���Ҳ�����ݼ�
			chaiSort(arr, first, mid, newarr);
			//���Ҳ��
			chaiSort(arr, mid+1, last, newarr);
			
			//��
			merge(arr, first, mid, last, newarr);
		}
	}
	
	
	//��   ����
	/**
	 * newarr ����  ===  ԭ����arr�ĳ���
	 * @param arr
	 * @param first
	 * @param mid
	 * @param last
	 * @param newarr
	 */
	
	public static void merge(int[] arr,int first,int mid,int last,int[] newarr){
		//��һ�����ݼ�����ʼ�±�
		int m=first;
		int x=mid;//��һ�����ݼ�����ֹ���±�
		//�ڶ������ݼ�����ʼ�±�
		int n=mid+1;
		int y=last;//�ڶ������ݼ�����ֹ�±�
		//�������  ��ʼ�±�
		int index=0;
		while(m<=x&&n<=y){
			if(arr[m]<=arr[n]){//��һ�����ݼ�������С
				newarr[index++]=arr[m++];
			}else{
				newarr[index++]=arr[n++];
			}
		}
		
		//���   ��һ�����ݼ�
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
