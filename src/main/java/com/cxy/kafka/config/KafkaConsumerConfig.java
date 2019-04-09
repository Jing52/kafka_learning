package com.cxy.kafka.config;

import com.cxy.kafka.common.MessageEntity;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${kafka.consumer.servers}")
    private String servers;

    //服务是否自动提交
    @Value("${kafka.consumer.enable.auto.commit}")
    private Boolean enableAutoCommit;

    //session超时时间
    @Value("${kafka.consumer.session.timeout}")
    private String sessionTimeout;

    //提交的间隔
    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;

    //生产者groupId
    @Value("${kafka.consumer.group.id}")
    private String groupId;

    //自动将offset重置到某位置
    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;

    //并发数目
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;

    public Map<String,Object> consumerConfigs() {
        Map<String,Object> props = new HashMap<String, Object>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    public ConsumerFactory<String, MessageEntity> consumerFactory(){
        return new DefaultKafkaConsumerFactory<String, MessageEntity>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<MessageEntity>());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MessageEntity>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, MessageEntity> factory = new ConcurrentKafkaListenerContainerFactory<String, MessageEntity>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }
}
