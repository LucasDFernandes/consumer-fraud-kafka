package br.com.alura.fraud.kafka.lib;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {

	void consume(ConsumerRecord<String, T> record) throws InterruptedException, ExecutionException;
	
}
