package br.com.alura.fraud.kafka.lib;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import br.com.alura.fraud.kafka.deserializer.GsonSerializer;

public class KafkaDispatcher<T> implements Closeable {

	private KafkaProducer<String, T> producer;

	public KafkaDispatcher() {
		this.producer = new KafkaProducer<>(properties());
	}

	public void send(String topic, String key, T value) throws InterruptedException, ExecutionException {
		ProducerRecord<String, T> record = new ProducerRecord<>(topic, key, value);
		producer.send(record, extractedListener()).get();
	}

	private static Callback extractedListener() {
		return (data, ex) -> {
			if (ex != null) {
				ex.printStackTrace();
				return;
			}
			System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset "
					+ data.offset() + "/ timestamp " + data.timestamp());
		};
	}

	private Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "0.0.0.0:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
		return properties;
	}

	@Override
	public void close() {
		producer.close();
	}

}
