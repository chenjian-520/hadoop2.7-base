package com.aura.hbase.day07;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class MyKakfkaParitioner implements Partitioner{

	// 管理和加载配置信息的。
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 
	 */
	@Override
	public int partition(String topic, 
			Object key, byte[] keyBytes, 
			Object value, byte[] valueBytes, 
			Cluster cluster) {

			try {
				String strKey = new String(keyBytes, "UTF-8");
//				String strValue = new String(valueBytes, "UTF-8");
				
				Integer ptns = cluster.partitionCountForTopic(topic);
				
				int ptn_index = (strKey.hashCode() & Integer.MAX_VALUE) % ptns;
				System.out.println(strKey + " 的分区编号为： " + ptn_index);
				
				return ptn_index;
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
