package com.cxy.kafka.producer;

import com.cxy.kafka.common.MessageEntity;
import com.cxy.kafka.consumer.SimpleConsumer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Slf4j
public class ProducerCallback implements ListenableFutureCallback<SendResult<String, MessageEntity>> {
    public static final Logger logger = Logger.getLogger(ProducerCallback.class);

    private final long startTime;

    private final String key;

    private final MessageEntity messageEntity;

    private final Gson gson = new Gson();

    public ProducerCallback(long startTime, String key, MessageEntity messageEntity){
        this.startTime = startTime;
        this.key = key;
        this.messageEntity = messageEntity;
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(@Nullable SendResult<String, MessageEntity> stringMessageEntitySendResult) {
        if(stringMessageEntitySendResult == null){
            return;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        RecordMetadata metadata = stringMessageEntitySendResult.getRecordMetadata();
        if(metadata!=null){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("message(")
                    .append("key= ").append(key).append(",")
                    .append("message= ").append(gson.toJson(messageEntity)).append(")")
                    .append("sent to partition(").append(metadata.partition()).append(")")
                    .append("with offset(").append(metadata.offset()).append(")")
                    .append("in ").append(elapsedTime).append(" ms");
            logger.info(stringBuffer.toString());
        }
    }
}
