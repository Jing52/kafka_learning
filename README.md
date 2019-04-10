![Kafka](https://upload-images.jianshu.io/upload_images/14481291-e1295f1367533f1a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Apache Kafka起源于LinkedIn，后来于2011年成为开源Apache项目，然后于2012年成为First-class Apache项目。Kafka是用Scala和Java编写的。 Apache Kafka是基于发布订阅的容错消息系统。 它是快速，可扩展和设计分布。

#  kafka介绍
## 主要功能
根据官网的介绍，ApacheKafka®是一个分布式流媒体平台，它主要有3种功能：
1. 发布和订阅消息流，这个功能类似于消息队列，这也是kafka归类为消息队列框架的原因

2. 以容错的方式记录消息流，kafka以文件的方式来存储消息流

3. 可以再消息发布的时候进行处理

## 使用场景
* 在系统或应用程序之间构建可靠的用于传输实时数据的管道，消息队列功能
* 构建实时的流数据处理程序来变换或处理数据流，数据处理功能
## 基本概念
`Producer`: 消息和数据的生产者，向kafka的一个Topic发布消息的进程/代码/服务

`Consumer`:消息和数据的消费者，订阅数据（topic）并且处理其发布的消息的进程/代码/服务

`Consumer Group`:逻辑概念，对于同一个topic，会广播给不同的group，一个group中，只有一个consumer可以消费该消息

`Broker`:物理概念，kafka集群中的每个kafka节点

`Topic`:逻辑概念，kafka消息的类别，对数据进行区分、隔离

`Partition`:物理概念，kafka下数据存储的基本单元。一个topic数据，会被分散存储到多个Partition，每一个Partition是有序的

`Replication`:同一个Partition可能会多个Replica，多个Replica之间数据是一样的

`Replication Leader`:一个Partition的多个Replica上，需要一个Leader负责该Partition上与Producer和Consumer交互

`ReplicaManager`:负责管理当前broker所有分区和副本的信息，处理KafkaController发起的一些请求，副本状态的切换、添加/读取消息等

## 基本概念延伸
### Partition
* 每一个Topic被切分为多个Partitions
* 消费者数目少于或等于Partition的数目
* Broker Group中的每一个Broker保存Topic的一个或多个Partitions
* Consumer Group中的仅有一个Consumer读取Topic的一个或多个Partitions，并且是唯一的Consumer
### Replication
* 当集群中有Broker挂掉的情况，系统可以主动地使Replicas提供服务
* 系统默认设置每一个Topic的Replication系数为1，可以在创建Topic时单独设置
#### Replication特点
* Replication的基本单位是Topic的Partition
* 所有的读和写都从Leader进，Followers只是作为备份
* Follower必须能够及时复制Leader的数据
* 增加容错性和可扩展性
##   kafka基本结构
### 消息传输流程
![消息传输流程](https://upload-images.jianshu.io/upload_images/14481291-7e10f04a7d8c7675.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* Producers API
* Consumers API
* Streams API
* Connectors API
下图更好的展示了kafka消息队列模式的运作
![kafka消息队列模式](https://upload-images.jianshu.io/upload_images/14481291-aded29d894cf6e21.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### kafka的消息结构

![消息结构](https://upload-images.jianshu.io/upload_images/14481291-c692a936c1f1dc8d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## kafka特点
### 分布式
* 多分区
* 多副本
* 多订阅者
* 基于ZooKeeper调度
### 高性能
* 高吞吐量
* 低延迟
* 高并发
* 时间复杂度为O(1)
### 持久性和扩展性
* 数据可持久化
* 容错性
* 支持水平在线扩展
* 消息自动平衡
## 应用场景
* 消息队列
* 行为跟踪
* 元信息监控
* 日志收集
* 流处理
* 事件源
* 持久性日志（commit log）
* 等
## kafka简单案例
### 下载与安装
#### ZooKeeper下载
```
http://zookeeper.apache.org/releases.html#download
```
#### Kafka下载

```
http://kafka.apache.org/downloads
```
#### 安装
解压、配置环境变量
PS：如果是Mac的话，有一个便捷安装方法，`brew install kafka`
### 启动
#### 运行Zookeeper
运行cmd命令窗口，输入zkServer回车，出现下图的就表示zookeeper启动成功，也表明安装成功了。
![Zookeeper](https://upload-images.jianshu.io/upload_images/14481291-a9de232b3e40d659.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 启动kafka
在新的cmd命令行用cd命令切换到kafka根目录`..\kafka_2.11-2.2.0`，输入命令
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```
![kafka](https://upload-images.jianshu.io/upload_images/14481291-db8c74589b3b15c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
出现started (kafka.server.KafkaServer)字样表示启动成功
#### 创建一个Topic
运行新的cmd命令行，进入`..\kafka_2.11-2.2.0\bin\windows`，创建主题：`test`，输入命令
```
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```
#### 创建一个Producer
运行新的cmd命令行，进入`..\kafka_2.11-2.2.0\bin\windows`，输入命令
```
kafka-console-producer.bat --broker-list localhost:9092 --topic test
```
#### 创建一个Consumer
运行新的cmd命令行，进入`..\kafka_2.11-2.2.0\bin\windows`，输入命令
```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
```
#### 测试
在Producer窗口下输入信息进行测试 ，输入的消息立马就会出现在Consumer中，表明kafka已经安装测试成功
![success](https://upload-images.jianshu.io/upload_images/14481291-592b958069790315.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## kafka代码案例
`项目结构`
![结构](https://upload-images.jianshu.io/upload_images/14481291-4948f7c4ee8d758b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

`pom.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.cxy</groupId>
    <artifactId>kafka</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>kafka</name>
    <description>kafka_learning</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.5</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j</artifactId>
            <version>1.3.5.RELEASE</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```
`application.properties`
```
kafka.consumer.zookeeper.connect=127.0.0.1:2181
kafka.consumer.servers=127.0.0.1:9092
kafka.consumer.enable.auto.commit=true
kafka.consumer.session.timeout=6000
kafka.consumer.auto.commit.interval=100
kafka.consumer.auto.offset.reset=latest
kafka.consumer.topic=kafkaTestTopic
kafka.consumer.group.id=kafkaTest
kafka.consumer.concurrency=10

kafka.producer.servers=127.0.0.1:9092
kafka.producer.retries=0
kafka.producer.batch.size=4096
kafka.producer.linger=1
kafka.producer.buffer.memory=40960

kafka.topic.default=kafkaTestTopic
```
`log4j.properties`
```# Global logging configuration 开发时候建议使用 debug
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```
`ErrorCode.java`
```
package com.cxy.kafka.common;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
public class ErrorCode {
    public final static int SUCCESS = 200;
    public final static int EXCEPTION = 500;
}
```
`MessageEntity.java`
```
package com.cxy.kafka.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Getter
@Setter
@EqualsAndHashCode
public class MessageEntity {
    private String title;
    private String body;

    @Override
    public String toString(){
        return "MessageEntity{" +
                "title='" + title + '\'' +
                ",body='" + body + '\'' +
                '}';
    }
}
```
`Response.java`
```
package com.cxy.kafka.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Getter
@Setter
public class Response {
    private int code;
    private String message;

    public Response(int code, String message){
        this.code = code;
        this.message = message;
    }
}
```
`KafkaProducerConfig.java`
```
package com.cxy.kafka.config;

import com.cxy.kafka.common.MessageEntity;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    @Value("${kafka.producer.servers}")
    private String servers;

    @Value("${kafka.producer.retries}")
    private int retries;

    @Value("${kafka.producer.batch.size}")
    private int batchSize;

    @Value("${kafka.producer.linger}")
    private int linger;

    @Value("${kafka.producer.buffer.memory}")
    private int bufferMemory;

    public Map<String,Object> producerConfigs() {
        Map<String,Object> props = new HashMap<String, Object>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    public ProducerFactory<String, MessageEntity> producerFactory(){
        return new DefaultKafkaProducerFactory<String, MessageEntity>(
                producerConfigs(),
                new StringSerializer(),
                new JsonSerializer<MessageEntity>());
    }

    @Bean
    public KafkaTemplate<String, MessageEntity> kafkaTemplate(){
        return new KafkaTemplate<String, MessageEntity>(producerFactory());
    }
}
```
`KafkaConsumerConfig.java`
```
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
    private boolean enableAutoCommit;

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

    private Map<String,Object> consumerConfigs() {
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

    private ConsumerFactory<String, MessageEntity> consumerFactory() {
        return new DefaultKafkaConsumerFactory<String, MessageEntity>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<MessageEntity>(MessageEntity.class));
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
```
`ProducerCallback.java`
```
package com.cxy.kafka.producer;

import com.cxy.kafka.common.MessageEntity;
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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("message(")
                    .append("key= ").append(key).append(",")
                    .append("message= ").append(gson.toJson(messageEntity)).append(")")
                    .append("sent to partition(").append(metadata.partition()).append(")")
                    .append("with offset(").append(metadata.offset()).append(")")
                    .append("in ").append(elapsedTime).append(" ms");
            logger.info(stringBuilder.toString());
        }
    }
}
```
`SimpleProducer.java`
```
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
```
`SimpleConsumer.java`
```
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
```
`ProducerController.java`
```
package com.cxy.kafka.controller;

import com.cxy.kafka.common.ErrorCode;
import com.cxy.kafka.common.MessageEntity;
import com.cxy.kafka.common.Response;
import com.cxy.kafka.producer.SimpleProducer;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class ProducerController {
    public static final Logger logger = Logger.getLogger(ProducerController.class);

    @Autowired
    private SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    private Gson gson = new Gson();

    @RequestMapping(value="/hello",method = RequestMethod.GET, produces = {"application/json"})
    public Response sendKafka(){
        return new Response(ErrorCode.SUCCESS,"SUCCESS");
    }

    @RequestMapping(value="/send",method = RequestMethod.POST, produces = {"application/json"})
    public Response sendKafka(@RequestBody MessageEntity messageEntity){
        try{
            logger.info("kafka消息:{}"+gson.toJson(messageEntity));
            simpleProducer.send(topic,"key",messageEntity);
            logger.info("发送kafka成功！");
            return new Response(ErrorCode.SUCCESS,"发送kafka成功！");
        }catch(Exception e){
            logger.error("发送kafka失败！",e);
            return new Response(ErrorCode.EXCEPTION,"发送kafka失败！");
        }
    }
}
```
`启动`
启动`KafkaApplication.java`。(必须打开zookeeper和kafka)
`测试`
我这边用的是`Restlet Client`插件，你们也可以根据自己喜好选择测试工具
* 先测试下`http://localhost:8080/kafka/hello`
![hello](https://upload-images.jianshu.io/upload_images/14481291-4589209e64c2837c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 测试`http://localhost:8080/kafka/send`
![send](https://upload-images.jianshu.io/upload_images/14481291-83da8ee4d338b801.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
发送kafka消息成功，去看下控制台，成功接收到kafka消息
![success](https://upload-images.jianshu.io/upload_images/14481291-69e302d01c8bea76.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 源代码
[源代码](https://github.com/nullcxy/kafka_learning)
