//package com.miaoshaproject.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
///**
// * @author DillonXie
// * @version 1.0
// * @date 11/24/2019 7:33 PM
// */
//@Configuration
//public class RabbitConfig {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//
//
//    public static final String EXCHANGE_A = "my-mq-exchange_A";
//    public static final String EXCHANGE_B = "my-mq-exchange_B";
//    public static final String EXCHANGE_C = "my-mq-exchange_C";
//
//
//    public static final String QUEUE_A = "QUEUE_A";
//    public static final String QUEUE_B = "QUEUE_B";
//    public static final String QUEUE_C = "QUEUE_C";
//    public static final String QUEUE_F1 = "QUEUE_F1";
//    public static final String QUEUE_F2 = "QUEUE_F2";
//    public static final String QUEUE_F3 = "QUEUE_F3";
//
//
//
//    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
//    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
//    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost("/");
//        connectionFactory.setPublisherConfirms(true);
//        return connectionFactory;
//    }
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    //必须是prototype类型
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory());
//        return template;
//    }
//
//    /**
//     * DirectExchange方式
//     * 在绑定交换机和queue时，需要制定routekey
//     * 发送消息时，需要指定交换机和routkey
//     * 接收消息时，只需要监听queuename即可
//     * 对于监听同一queue的多个消费者，采用负载均衡的方式消费
//     * @return
//     */
//    //创建Direct交换机
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(EXCHANGE_A);
//    }
//    //创建对应queue
//    @Bean
//    public Queue queueA() {
//        return new Queue(QUEUE_A, true);
//    }
//    @Bean
//    public Queue queueB() {
//        return new Queue(QUEUE_B, true);
//    }
//    @Bean
//    public Queue queueC() {
//        return new Queue(QUEUE_C, true);
//    }
//    //绑定交换机和queue
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
//    }
//    @Bean
//    public Binding binding2() {
//        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_B);
//    }
//    @Bean
//    public Binding binding3() {
//        return BindingBuilder.bind(queueC()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_C);
//    }
//
//
//    /**
//     * FanoutExchange方式
//     * 在绑定交换机和queue时，不需要指定routekey
//     * 发送消息时，需要指定交换机，routkey设置为null
//     * 接收消息时，只需要监听queuename即可
//     * 每发送一条消息，绑定了FanoutExchange交换机的所有queue都会收到消息
//     * @return
//     */
//    //创建Fanout交换机
//    @Bean
//    public FanoutExchange createFanoutExchange() {
//        return new FanoutExchange(EXCHANGE_B);
//    }
//    //创建对应Queue
//    @Bean
//    public Queue queueF1() {
//        return new Queue(QUEUE_F1, true);
//    }
//    @Bean
//    public Queue queueF2() {
//        return new Queue(QUEUE_F2, true);
//    }
//    @Bean
//    public Queue queueF3() {
//        return new Queue(QUEUE_F3, true);
//    }
//    //绑定交换机和queue
//    @Bean
//    public Binding bindingF1() {
//        return BindingBuilder.bind(queueF1()).to(createFanoutExchange());
//    }
//    @Bean
//    public Binding bindingF2() {
//        return BindingBuilder.bind(queueF2()).to(createFanoutExchange());
//    }
//    @Bean
//    public Binding bindingF3() {
//        return BindingBuilder.bind(queueF3()).to(createFanoutExchange());
//    }
//
//
//}
