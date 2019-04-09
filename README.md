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
```
启动zookeeper命令: zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties

启动kafka命令： 进入kafka安装目录，cd /usr/local/Cellar/kafka/1.1.0/

启动命令：./bin/kafka-server-start /usr/local/etc/kafka/server.properties

启动kafka生产者：./bin/kafka-console-producer --broker-list localhost:9092 --topic imooc-kafka-topic
```
## kafka代码案例


