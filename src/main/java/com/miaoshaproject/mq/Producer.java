//package com.miaoshaproject.mq;
//
//import com.miaoshaproject.config.RabbitConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
///**
// * @author DillonXie
// * @version 1.0
// * @date 11/25/2019 7:54 PM
// */
//@Component
//public class Producer implements RabbitTemplate.ConfirmCallback{
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    public Producer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//        rabbitTemplate.setConfirmCallback(this);
//    }
//
//    public void sendMsg(String content) {
//        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, null, content, correlationId);
//    }
//
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        logger.info(" 回调id:" + correlationData);
//        if (ack) {
//            logger.info("消息成功消费");
//        } else {
//            logger.info("消息消费失败:" + cause);
//        }
//    }
//}
