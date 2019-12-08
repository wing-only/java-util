package com.wing.java.util.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.support.ProducerListener;

/**
 * kafkaProducer监听器，在producer配置文件中开启
 */
@SuppressWarnings("all")
@Slf4j
public class KafkaProducerListener implements ProducerListener {

    /**
     * 发送消息成功后调用
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        log.debug("[Kafka Producer Listener]kafka send success start");
        log.debug("[Kafka Producer Listener]topic:" + topic);
        log.debug("[Kafka Producer Listener]partition:" + partition);
        log.debug("[Kafka Producer Listener]key:" + key);
        log.debug("[Kafka Producer Listener]value:" + value);
        log.debug("[Kafka Producer Listener]RecordMetadata:" + recordMetadata);
        log.debug("[Kafka Producer Listener]kafka send success end");
    }

    /**
     * 发送消息错误后调用
     */
    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {
        log.error("[Kafka Producer Listener]kafka send error start");
        log.error("[Kafka Producer Listener]topic:" + topic);
        log.error("[Kafka Producer Listener]partition:" + partition);
        log.error("[Kafka Producer Listener]key:" + key);
        log.error("[Kafka Producer Listener]value:" + value);
        log.error("[Kafka Producer Listener]Exception:" + exception);
        log.error("[Kafka Producer Listener]kafka send error end");
        exception.printStackTrace();
        throw new KafkaException(exception.getMessage());
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    @Override
    public boolean isInterestedInSuccess() {
        log.debug("[Kafka Producer Listener]kafkaProducer listener started");
        return true;
    }

}