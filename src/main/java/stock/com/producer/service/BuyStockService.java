package stock.com.producer.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import stock.com.producer.publish.PublishMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class BuyStockService {

    @Autowired
    private PublishMessage publishMessage;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String TOPIC = "stock-orders";

    public void buyStocks(Map<String, Object> requestBody) {
        try {
            String message = objectMapper.writeValueAsString(requestBody);
            publishMessage.publish(message,TOPIC);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
