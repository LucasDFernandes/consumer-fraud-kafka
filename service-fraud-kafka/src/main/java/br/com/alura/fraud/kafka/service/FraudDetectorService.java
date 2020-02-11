package br.com.alura.fraud.kafka.service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.fraud.kafka.domain.Order;
import br.com.alura.fraud.kafka.lib.KafkaDispatcher;

public class FraudDetectorService {
	
	private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<Order>();

	public void parse(ConsumerRecord<String, Order> record) throws InterruptedException, ExecutionException {
		System.out.println("---------------------------------------------");
		System.out.println("Processing new order, checking for fraud");
		System.out.println(record.key());
		System.out.println(record.value());
		System.out.println(record.partition());
		System.out.println(record.offset());

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (verificaFraude(record.value().getAmount())) {
			System.out.println("Order is a fraud !!!");
			orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", record.value().getUserId(), record.value());
		} else {
			System.out.println("Approved: " + record.value());
		}

		System.out.println("---------------------------------------------");
	}

	/**
	 * 
	 * Fingindo Regra para dar erro em alguns dados
	 * 
	 * @param valorOrdem
	 * @return
	 */
	private boolean verificaFraude(BigDecimal valorOrdem) {
		return valorOrdem.compareTo(BigDecimal.valueOf(55)) >= 0;
	}

}
