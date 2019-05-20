package com.aura.hbase.day07;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 通过这个KafkaProducerOps向Kafka topic中生产相关的数据
 */
public class MyKafkaProducer {
	
	public static void main(String[] args) throws IOException {
		
		/**
		 * 专门加载配置文件
		 * 配置文件的格式：
		 * key=value
		 * 在代码中要尽量减少硬编码
		 * 不要将代码写死，要可配置化
		 * 
		 * 第一种方式：
		 * 	  通过配置文件制定
		 */
		Properties properties = new Properties();
		InputStream in = MyKafkaProducer.class.getClassLoader().getResourceAsStream("producer.properties");
		properties.load(in);
		
		
		/**
		 * 第二种方式；
		 * 	自己在代码中进行设置
		 */
//		 Properties props = new Properties();
//		 props.put("bootstrap.servers", "localhost:9092");
//		 props.put("acks", "all");
//		 props.put("retries", 0);
//		 props.put("batch.size", 16384);
//		 props.put("linger.ms", 1);
//		 props.put("buffer.memory", 33554432);
//		 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		
		/**
		 * 重点API就是:  KafkaProducer
		 * 
		 * 发送消息  ,  相当于 push 消息到了  kafka的对应 topic中去了。
		 * 重要的方法： producer.send(producerRecord);
		 */
		
		
		/**
		 * 两个泛型参数
		 * 第一个泛型参数：指的就是kafka中一条记录key的类型
		 * 第二个泛型参数：指的就是kafka中一条记录value的类型
		 */
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		// kafka_test11
		String topic = properties.getProperty("producer.topic");
		String key = "key3";
		String value = "我爱北京天安门3";
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, key, value);
		
		/**
		 * 发送消息  ,  相当于 push 消息到了  kafka的对应 topic中去了。
		 */
		producer.send(producerRecord);
		producer.close();
	}
}