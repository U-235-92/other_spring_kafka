package com.other.app.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	@KafkaListener(topics = "${kafka.properties.topic-name}")
	public void receipt(@Payload String message) {
		System.out.println("Receipted message: " + message);
	}
}
