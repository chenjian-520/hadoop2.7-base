package com.aura.hbase.hbase.filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 作者： 马中华   https://blog.csdn.net/zhongqi2513
 * 时间： 2017/11/30 16:21
 * 描述： 多种过滤条件的使用方法
 */
public class HBase_Filter01 {

    // 声明静态配置
    static Configuration conf = null;

    private static final String ZK_CONNECT_STR = "hadoop02:2181,hadoop03:2181,hadoop04:2181,hadoop05:2181";

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", ZK_CONNECT_STR);
    }

    public static void main(String[] args) throws IOException {

        HTable table = new HTable(conf, "person".getBytes());
        Scan scan = new Scan(Bytes.toBytes("person_zhang_000001"), Bytes.toBytes("person_zhang_000002"));

        //前缀过滤器----针对行键
        Filter filter = new PrefixFilter(Bytes.toBytes("person"));

        //行过滤器  ---针对行键
        ByteArrayComparable rowComparator = new BinaryComparator(Bytes.toBytes("person_zhang_000001"));
        RowFilter rf = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, rowComparator);

        rf = new RowFilter(CompareFilter.CompareOp.EQUAL , new SubstringComparator("_2016-12-31_"));

        //单值过滤器1完整匹配字节数组
        new SingleColumnValueFilter("base_info".getBytes(), "name".getBytes(), CompareFilter.CompareOp.EQUAL, "zhangsan".getBytes());

        //单值过滤器2 匹配正则表达式
        ByteArrayComparable comparator = new RegexStringComparator("zhang.");
        new SingleColumnValueFilter("info".getBytes(), "NAME".getBytes(), CompareFilter.CompareOp.EQUAL, comparator);

        //单值过滤器3匹配是否包含子串,大小写不敏感
        comparator = new SubstringComparator("wu");
        new SingleColumnValueFilter("info".getBytes(), "NAME".getBytes(), CompareFilter.CompareOp.EQUAL, comparator);

        //键值对元数据过滤-----family过滤----字节数组完整匹配
        FamilyFilter ff = new FamilyFilter(CompareFilter.CompareOp.EQUAL ,
                new BinaryComparator(Bytes.toBytes("base_info"))   //表中不存在inf列簇，过滤结果为空
        );

        //键值对元数据过滤-----family过滤----字节数组前缀匹配
        ff = new FamilyFilter(
                CompareFilter.CompareOp.EQUAL ,
                new BinaryPrefixComparator(Bytes.toBytes("inf"))   //表中存在以inf打头的列簇info，过滤结果为该列簇所有行
        );

        //键值对元数据过滤-----qualifier过滤----字节数组完整匹配
        filter = new QualifierFilter(
                CompareFilter.CompareOp.EQUAL ,
                new BinaryComparator(Bytes.toBytes("na"))   //表中不存在na列，过滤结果为空
        );
        filter = new QualifierFilter(
                CompareFilter.CompareOp.EQUAL ,
                new BinaryPrefixComparator(Bytes.toBytes("na"))   //表中存在以na打头的列name，过滤结果为所有行的该列数据
        );

        //基于列名(即Qualifier)前缀过滤数据的ColumnPrefixFilter
        filter = new ColumnPrefixFilter("na".getBytes());

        //基于列名(即Qualifier)多个前缀过滤数据的MultipleColumnPrefixFilter
        byte[][] prefixes = new byte[][] {Bytes.toBytes("na"), Bytes.toBytes("me")};
        filter = new MultipleColumnPrefixFilter(prefixes);

        //为查询设置过滤条件
        scan.setFilter(filter);

        scan.addFamily(Bytes.toBytes("base_info"));
        //一行
//		Result result = table.get(get);
        //多行的数据
        ResultScanner scanner = table.getScanner(scan);
        for(Result r : scanner){
            /**
             for(KeyValue kv : r.list()){
             String family = new String(kv.getFamily());
             System.out.println(family);
             String qualifier = new String(kv.getQualifier());
             System.out.println(qualifier);
             System.out.println(new String(kv.getValue()));
             }
             */
            //直接从result中取到某个特定的value
            byte[] value = r.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
            System.out.println(new String(value));
        }
        table.close();
    }
}
