package stock.com.producer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stock.com.producer.publish.PublishMessage;

import java.util.Map;
import java.util.Random;

@Component
public class StockPrices {


    @Autowired
    private PublishMessage publishMessage;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TOPIC = "stock-prices";

    private final Random random = new Random();

    @Scheduled(fixedRate = 2000)
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
