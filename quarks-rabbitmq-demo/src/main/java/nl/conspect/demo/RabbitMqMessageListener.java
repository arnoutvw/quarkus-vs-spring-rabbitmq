package nl.conspect.demo;

import io.micrometer.core.annotation.Timed;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RabbitMqMessageListener {

    Logger logger = LoggerFactory.getLogger(RabbitMqMessageListener.class);

    @Channel("out-1")
    Emitter<String> emitter;

    @Incoming("in-1")
    @Blocking("quarkus-rabbitmq-pool")
    @Timed(value = "in-1")
    public CompletionStage<Void> receiveMessage(Message<?> incomingMessage) {

        if (logger.isDebugEnabled()) {
            logger.debug("Received message: {}", new String((byte[]) incomingMessage.getPayload(), StandardCharsets.UTF_8));
        }

        try {
            Thread.sleep(100L); // processing
        } catch (InterruptedException e) {
            return incomingMessage.nack(e);
        }

        emitter.send(Message.of(new String((byte[]) incomingMessage.getPayload(), StandardCharsets.UTF_8)));
        return incomingMessage.ack();
    }
}
