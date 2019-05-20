package com.aura.hbase.day07;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import kafka.utils.VerifiableProperties;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class RoundRobinPartitioner implements Partitioner {
  
  private static AtomicLong next = new AtomicLong();

  public RoundRobinPartitioner(VerifiableProperties verifiableProperties) {}

  @Override
  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
    long nextIndex = next.incrementAndGet();
    return (int)nextIndex % cluster.partitionCountForTopic("topic1");
  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> configs) {

  }
}


