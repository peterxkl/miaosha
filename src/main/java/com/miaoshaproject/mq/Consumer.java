//package com.miaoshaproject.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.miaoshaproject.config.RabbitConfig;
//import com.miaoshaproject.dao.ItemStockDOMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * @author DillonXie
// * @version 1.0
// * @date 11/25/2019 8:01 PM
// */
//@Component
//@RabbitListener(queues = RabbitConfig.QUEUE_F1)
//public class Consumer {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private ItemStockDOMapper itemStockDOMapper;
//
//    @RabbitHandler
//    public void process(String content) {
//        Map<String, Object> map = JSON.parseObject(content, Map.class);
//        Integer itemId = (Integer)map.get("itemId");
//        Integer amount = (Integer)map.get("amount");
//        itemStockDOMapper.decreaseStock(itemId, amount);
//        logger.info("处理器one接收处理队列F1当中的消息： " + content);
//    }
//}
