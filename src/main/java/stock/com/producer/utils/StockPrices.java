package stock.com.producer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stock.com.producer.publish.PublishMessage;

import java.util.Map;
import java.util.Random;

@Component
public class StockPrices {

    private static final Logger LOG = LoggerFactory.getLogger(StockPrices.class);

    @Autowired
    private PublishMessage publishMessage;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TOPIC = "stock-prices";

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void stocksPrices() {
        try {
            Map<String,Object> stockPrices = Map.of(
                    "Nifty-50", 1500.00 + random.nextDouble() * 100,
                    "SBI", 700.00 + random.nextDouble() * 100,
                    "Axis", 800.00 + random.nextDouble() * 100,
                    "TCS", 210.00 + random.nextDouble() * 10
            );
            String message = objectMapper.writeValueAsString(stockPrices);
            publishMessage.publish(message,TOPIC);
            LOG.info("send current stock price to the topic {} , {}",TOPIC, message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
