package stock.com.producer.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PublishMessage {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PublishMessage.class);

    public void publish(String message,String topic) {
        kafkaTemplate.send(topic, message);
        logger.info("Message published to Kafka topic: {},{}", topic, message);
    }
}
