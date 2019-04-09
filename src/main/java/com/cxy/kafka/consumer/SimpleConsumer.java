package com.cxy.kafka.consumer;

import com.cxy.kafka.common.MessageEntity;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Slf4j
@Component
public class SimpleConsumer {
    public static final Logger logger = Logger.getLogger(SimpleConsumer.class);

    private final Gson gson = new Gson();

    @KafkaListener(topics = "${kafka.topic.default}",containerFactory = "kafkaListenerContainerFactory")
    public void receive(MessageEntity messageEntity){
        logger.info(gson.toJson(messageEntity));
    }
}
