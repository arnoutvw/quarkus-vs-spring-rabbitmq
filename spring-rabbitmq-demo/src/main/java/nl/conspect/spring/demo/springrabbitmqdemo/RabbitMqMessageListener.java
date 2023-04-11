package nl.conspect.spring.demo.springrabbitmqdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RabbitMqMessageListener {

    final AmqpTemplate sender;

    Logger logger = LoggerFactory.getLogger(RabbitMqMessageListener.class);

    public RabbitMqMessageListener(AmqpTemplate sender) {
        this.sender = sender;
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "in-exchange", type = "topic"),
            key = "1",
            value = @Queue(value = "in-1")
    ))
    @Transactional
    public void receivedMessage(Message<String> incomingMessage) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received message: {}", incomingMessage.getPayload());
        }

        try {
            Thread.sleep(100L); // processing
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sender.convertAndSend("out-exchange", "1", incomingMessage.getPayload());
    }
}
