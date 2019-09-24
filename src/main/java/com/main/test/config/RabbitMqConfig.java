package com.main.test.config;

import com.main.test.common.rabbitmq.convert.Jackson2JavaTypeMapper;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    @Autowired
    SpringTestProperties springTestProperties;

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        Jackson2JavaTypeMapper javaTypeMapper = new Jackson2JavaTypeMapper();
        javaTypeMapper.setTrustedPackages("*");
        jsonConverter.setJavaTypeMapper(javaTypeMapper);
        return jsonConverter;
    }

    @Bean
    public CachingConnectionFactory secondConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(springTestProperties.getSecondRabbitMq().getHost());
        factory.setPort(springTestProperties.getSecondRabbitMq().getPort());
        factory.setUsername(springTestProperties.getSecondRabbitMq().getUserName());
        factory.setPassword(springTestProperties.getSecondRabbitMq().getPassword());
        factory.setVirtualHost(springTestProperties.getSecondRabbitMq().getVirtualHost());
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory secondListenerContainerFactory(ConnectionFactory secondConnectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(secondConnectionFactory);
        containerFactory.setPrefetchCount(2);
        containerFactory.setConcurrentConsumers(5);
        containerFactory.setMaxConcurrentConsumers(20);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //重试3次
        RetryInterceptorBuilder builder = RetryInterceptorBuilder.stateless();
        builder.maxAttempts(3).backOffOptions(1000,1,1000);
        builder.recoverer(new RejectAndDontRequeueRecoverer());
        containerFactory.setAdviceChain(builder.build());
        return containerFactory;
    }
}
