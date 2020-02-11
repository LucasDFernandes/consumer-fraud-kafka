package br.com.alura.fraud.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.alura.fraud.kafka.domain.Order;

public class FraudDetectorService {

	public void parse(ConsumerRecord<String, Order> record) {
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
		System.out.println("Order processed");
		System.out.println("---------------------------------------------");
	}

}
