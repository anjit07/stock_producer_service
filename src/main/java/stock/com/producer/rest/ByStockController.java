package stock.com.producer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import stock.com.producer.service.BuyStockService;

import java.util.Map;


@RestController
@RequestMapping(value = "/stock")
public class ByStockController {


    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BuyStockService buyStockService;

    @PostMapping( "/buy")
    public Mono<ResponseEntity<String>> buyStocks(@RequestBody Map<String, Object> requestBody) {
        LOG.info("Received request to buy stocks: {}", requestBody);
        buyStockService.buyStocks(requestBody);
        return Mono.just(ResponseEntity.ok("Order submitted"));
    }
}
