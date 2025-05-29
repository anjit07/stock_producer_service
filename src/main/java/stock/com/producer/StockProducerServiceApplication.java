package stock.com.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockProducerServiceApplication.class, args);
	}

}
