package com.jshy.kafka.sample;

import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 消费者
 */
public class ConsumerQuickStart {

    public static void main(String[] args) {
        //1.添加kafka的配置信息
        Properties properties = new Properties();
        //kafka的连接地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "110.40.221.30:9092");
        //消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        //消息的反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        //设置手动提交偏移量
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        //2.消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //3.订阅主题
        consumer.subscribe(Collections.singletonList("itcast-topic-out"));

        //异步同步提交偏移量
        try {
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.offset());
                    System.out.println(record.partition());
                }
                consumer.commitAsync();
            }
        }catch (Exception e){
                e.printStackTrace();
            System.out.println("记录错误信息："+e);
        }finally {
            try {
                consumer.commitSync();
            }finally {
                consumer.close();
            }
        }

//        //当前线程一直处于监听状态
//        while (true) {
//            //4.获取消息
//            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
//                    System.out.println(consumerRecord.key());
//                    System.out.println(consumerRecord.value());
//            }
//        }

//        //同步提交偏移量
//        while (true){
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(record.value());
//                System.out.println(record.key());
//                try {
//                    consumer.commitSync();//同步提交当前最新的偏移量
//                }catch (CommitFailedException e){
//                    System.out.println("记录提交失败的异常："+e);
//                }
//
//            }
//        }

//        //异步提交偏移量
//        while (true){
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(record.value());
//                System.out.println(record.key());
//                try {
//                    consumer.commitSync();//同步提交当前最新的偏移量
//                }catch (CommitFailedException e){
//                    System.out.println("记录提交失败的异常："+e);
//                }
//
//            }
//        }
    }

}