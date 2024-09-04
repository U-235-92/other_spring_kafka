package com.other.app.producer;

import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

	private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	@Value("${kafka.properties.topic-name}")
	private String topic;
	
	public void send(String message) {
		kafkaTemplate.send(topic, message);
		kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
			@Override
			public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
				LOGGER.info("[SUCCESS SEND A MESSAGE]");
			}
			
			@Override
			public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata,
					Exception exception) {
				LOGGER.info("[ERROR OCCURED DURING SEND A MESSAGE]");
			}
		});
	}
}
