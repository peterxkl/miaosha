package com.miaoshaproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.miaoshaproject.serializer.JodaDateTimeJsonDeserializer;
import com.miaoshaproject.serializer.JodaDateTimeSerializer;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
//下面语句是：配置session过期时间
public class RedisConfig {
    @Bean
    RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        //解决key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //解决value系列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //特殊处理DateTime格式数据的序列化
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(DateTime.class, new JodaDateTimeSerializer());
        simpleModule.addDeserializer(DateTime.class, new JodaDateTimeJsonDeserializer());
        objectMapper.registerModule(simpleModule);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
