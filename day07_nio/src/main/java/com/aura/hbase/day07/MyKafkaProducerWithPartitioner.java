package com.aura.hbase.day07;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * 作者： 马中华：http://blog.csdn.net/zhongqi2513
 * 日期： 2018年5月22日 下午1:56:50
 *
 * 描述： 通过这个MyKafkaProducerWithPartitioner向Kafka topic中生产相关的数据
 * 
 * 
 * Spark Streaming + Kafka + Flume + Redis
 * 
 * flume + kafka + storm + redis
 */
public class MyKafkaProducerWithPartitioner {
	
	public static void main(String[] args) throws IOException {
		
		/**
		 * 专门加载配置文件
		 * 配置文件的格式： key=value
		 * 在代码中要尽量减少硬编码 不要将代码写死，要可配置化
		 */
		Properties properties = new Properties();
		InputStream in = MyKafkaProducerWithPartitioner.class.getClassLoader().getResourceAsStream("producer.properties");
		properties.load(in);
//		properties.setProperty("partitioner.class", "com.aura.kafka1807.MyKakfkaParitioner");
		
		/**
		 * 两个泛型参数
		 * 第一个泛型参数：指的就是kafka中一条记录key的类型
		 * 第二个泛型参数：指的就是kafka中一条记录value的类型
		 */
		String[] mingxing = new String[] { "黄渤", "徐峥", "王宝强", "成龙", "李连杰", "甄子丹" };
		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		Random random = new Random();
		int start = 1;
		for (int i = start; i <= start + 20; i++) {
			String topic = "kafka_test11";
			String key = mingxing[random.nextInt(mingxing.length)];
			String value = "今天<--" + key + "-->动作很帅";
			
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, key, value);
			producer.send(producerRecord);
		}
		producer.close();
	}
}