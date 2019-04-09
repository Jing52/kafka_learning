package com.cxy.kafka.producer;

import com.cxy.kafka.common.MessageEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Component
public class SimpleProducer {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public void send(String topic, MessageEntity messageEntity){
        kafkaTemplate.send(topic,messageEntity);
    }

    public void send(String topic, String key, MessageEntity messageEntity){
        ProducerRecord<String, MessageEntity> record = new ProducerRecord<String, MessageEntity>(topic,key,messageEntity);
        long startTime = System.currentTimeMillis();
        ListenableFuture<SendResult<String, MessageEntity>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime,key,messageEntity));
    }
}
