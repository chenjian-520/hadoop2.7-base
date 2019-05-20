package com.ghgj.cn.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZkApi {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		//��ȡzk����
		ZooKeeper zk = new ZooKeeper("192.168.66.131:2181", 5000, null);
		//�����ļ�ϵͳ
		
		//�����ڵ� create
		/**
		 * ����1���ڵ�·��  string
		 * ����2���ڵ�����  byte[]
		 * ����3��Ȩ��  Ĭ��
		 * ����3���ڵ������
		 */
		/**
		 * ���õ� PERSISTENT
		 * ��ʱ�� PERSISTENT_SEQUENTIAL
		 * �����б�� EPHEMERAL
		 * ��ʱ�б�� EPHEMERAL_SEQUENTIAL
		 */
	//	zk.create("/test_api","nihenc".getBytes(),
	//			Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		//�޸Ľڵ�����
		/**
		 * ����1���ڵ�·��
		 * ����2���޸�����
		 * ����3������ �汾  �����֪�� -1 �����ȡ���°汾
		 */
	//	zk.setData("/test_api", "sss".getBytes(), -1);
		
		//ɾ���ڵ�  ֻ��ɾ���սڵ�
		//�ǿսڵ���Ҫ�ݹ�ɾ��
	//	zk.delete("/test_api", -1);
		
		//�鿴�ڵ����� get
		/**
		 * ����1���ڵ�·��
		 * ����2������   ��������null
		 * ����3���ڵ�״̬����  ��֪��  ��  null
		 */
	/*	byte[] data = zk.getData("/test_api", null, null);
		String con = new String(data);
		System.out.println(con);*/
		
		//�鿴�ӽڵ� ls
		/**
		 * ����1���ڵ�·��
		 * ����2������
		 */
		List<String> children = zk.getChildren("/", null);
		for(String c: children){
			System.out.println(c);
		}
		System.out.println(children);
		
	/*	//�жϽڵ��Ƿ����
		Stat exists = zk.exists("/text", null);
		//����ֵ��һ���ڵ�״̬����   ��װ�ڵ�״̬��Ϣ��   ��ǰ�ڵ㲻����  ����null
		System.out.println(exists);
		System.out.println(exists.getDataLength());*/
		
		
		
	}

}
