package com.aura.hbase.day07;

import kafka.admin.TopicCommand;

public class CreateTopic {
	
	public static void main(String[] args) {
		createTopic();
	}
	
	public static void createTopic() {
		/**
		 * 创建一个主题  topic
		 * 
		 * 
			kafka-topics.sh \
			--create \
			--zookeeper hadoop02:2181,hadoop03:2181,hadoop04:2181 \
			--replication-factor 3 \
			--partitions 10 \
			--topic kafka_test11
		 */
		
		String[] ops = new String[] {
				"--create", 
				"--zookeeper", 
				"hadoop02:2181,hadoop03:2181,hadoop04:2181",
				"--replication-factor", 
				"3", 
				"--partitions", 
				"10", 
				"--topic", 
				"kafka_test11" 
		};
		
		String[] ops1 = new String[]{
				
				"--list",
				"--zookeeper",
				"hadoop02:2181,hadoop03:2181,hadoop04:2181"
		};
		
		TopicCommand.main(ops1);
	}
}
