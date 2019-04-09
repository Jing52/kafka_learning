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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/kafka")
public class ProducerController {
    public static final Logger logger = Logger.getLogger(ProducerController.class);

    @Autowired
    private SimpleProducer simpleProducer;

    @Value("${kafka.topic.default}")
    private String topic;

    private Gson gson = new Gson();

    @RequestMapping(value="/hello",method = RequestMethod.GET, produces = "application/json")
    public Response sendKafka(){
        return new Response(ErrorCode.SUCCESS,"SUCCESS");
    }

    @RequestMapping(value="/send",method = RequestMethod.POST, produces = "application/json")
    public Response sendKafka(MessageEntity messageEntity){
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
