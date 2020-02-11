package br.com.alura.fraud.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.fraud.kafka.domain.Order;
import br.com.alura.fraud.kafka.lib.KafkaService;
import br.com.alura.fraud.kafka.service.FraudDetectorService;

@SpringBootApplication
public class ServiceFraudKafkaApplication {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		FraudDetectorService fraudDetectorService = new FraudDetectorService();
		try (KafkaService service = new KafkaService(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_ORDER_APPROVED",
				fraudDetectorService::parse, Order.class)) {
			service.run();
		}
		
		SpringApplication.run(ServiceFraudKafkaApplication.class, args);
	}

}
