package com.wing.java.util.kafka;

import com.wing.java.util.exception.ExceptionConstant;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * kafkaProducer模板
 * 使用此模板发送消息
 */
public class KafkaProducerUtil {

    private KafkaTemplate<String, String> kafkaTemplate;

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * kafka发送消息模板
     *
     * @param topic        主题
     * @param value        messageValue
     * @param ifPartition  是否使用分区 0不是\1是
     * @param partitionNum 分区数 如果是否使用分区为1, 分区数必须大于0
     * @param role         角色:bbc app erp ...
     */
    public Map<String, Object> sendMessage(String topic, Object value, String ifPartition, Integer partitionNum, String role) {
        String key = role + "-" + value.hashCode();
        String valueString = (String) value;
        if ("1".equals(ifPartition)) {
            //表示使用分区
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
            Map<String, Object> res = checkProRecord(result);
            return res;
        } else {
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, valueString);
            Map<String, Object> res = checkProRecord(result);
            return res;
        }
    }

    /**
     * 根据key值获取分区索引
     *
     * @param key
     * @param partitionNum
     * @return
     */
    private int getPartitionIndex(String key, int partitionNum) {
        if (key == null) {
            Random random = new Random();
            return random.nextInt(partitionNum);
        } else {
            int result = Math.abs(key.hashCode()) % partitionNum;
            return result;
        }
    }

    /**
     * 检查发送返回结果record
     *
     * @param result
     * @return
     */
    @SuppressWarnings("all")
    private Map<String, Object> checkProRecord(ListenableFuture<SendResult<String, String>> result) {
        Map<String, Object> m = new HashMap<String, Object>();
        if (result != null) {
            try {
                SendResult r = result.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = r.getRecordMetadata().offset();
                if (offsetIndex != null && offsetIndex >= 0) {
                    m.put("code", ExceptionConstant.SUCCESS);
                    m.put("message", ExceptionConstant.SUCCESS_MESSAGE);
                    return m;
                } else {
                    m.put("code", ExceptionConstant.KAFKA_NO_OFFSET_CODE);
                    m.put("message", ExceptionConstant.KAFKA_NO_OFFSET_MES);
                    return m;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                m.put("code", ExceptionConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", ExceptionConstant.KAFKA_SEND_ERROR_MES);
                return m;
            } catch (ExecutionException e) {
                e.printStackTrace();
                m.put("code", ExceptionConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", ExceptionConstant.KAFKA_SEND_ERROR_MES);
                return m;
            }
        } else {
            m.put("code", ExceptionConstant.KAFKA_NO_RESULT_CODE);
            m.put("message", ExceptionConstant.KAFKA_NO_RESULT_MES);
            return m;
        }
    }


}