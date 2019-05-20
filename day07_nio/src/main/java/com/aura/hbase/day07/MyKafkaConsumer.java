package com.aura.hbase.day07;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * 作者： 马中华：http://blog.csdn.net/zhongqi2513
 * 日期： 2018年5月22日 下午1:35:21
 *
 * 描述： 消费者程序
 */
public class MyKafkaConsumer {
	
	public static void main(String[] args) throws IOException {
		
		/**
		 * 第一种方式：
		 * 就是实现把  consumer.properties 这个文件的所有key-value对都加载到 properties 对象中去
		 */
		Properties properties = new Properties();
		InputStream in = MyKafkaConsumer.class.getClassLoader().getResourceAsStream("consumer.properties");
		properties.load(in);
//		properties.setProperty(key, value)
//		properties.getProperty(key)
		
		
		/**
		 * 第二种方式：
		 * 获取properties对象的方式叶可以使用下面这种
		 * 或者使用下面这种方式也是OK的
		 */
//		Properties properties = new Properties();
//		properties.put("bootstrap.servers", "hadoop02:9092");
//		properties.put("group.id", "hadoop");
//		properties.put("enable.auto.commit", "false");
//		properties.put("auto.commit.interval.ms", "1000");
//		properties.put("session.timeout.ms", "30000");
//		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		Consumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		Collection<String> topics = Arrays.asList("kafka_test11");
		
		// 消费者订阅topic
		consumer.subscribe(topics);
		
		ConsumerRecords<String, String> consumerRecords = null;
		
		while (true) {
			System.out.println("-------");
			
			// 接下来就要从topic中拉取数据
			// 其实是一个阻塞的方法： 阻塞1秒钟，   每隔一秒钟从检测一下对应的topic是否有消息可拿
			consumerRecords = consumer.poll(1000);
			
			// 遍历每一条记录
			for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
				String topic = consumerRecord.topic();
				long offset = consumerRecord.offset();
				int partition = consumerRecord.partition();
				String key = consumerRecord.key();
				String value = consumerRecord.value();
				System.out.format("%s\t%d\t%d\t%s\t%s\n", topic, offset, partition, key, value);
			}
		}
	}
}