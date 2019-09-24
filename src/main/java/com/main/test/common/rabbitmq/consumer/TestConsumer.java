package com.main.test.common.rabbitmq.consumer;


import com.main.test.constants.RabbitMqConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(containerFactory = "secondConnectionFactory", bindings = @QueueBinding(
        value = @Queue(value = RabbitMqConstants.QUEUE, durable = "true"),
        exchange = @Exchange(value = RabbitMqConstants.EXCHANGE),
        key = RabbitMqConstants.QUEUE_KEY))
public class TestConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void getMsg(@Payload String msg) {
        logger.debug(msg);

    }


}
