package com.other.app.consumer;

import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName()); 
	
//	@KafkaListener(topics = {"${kafka.properties.topic-name.a}"}, groupId = "A-CONSUMERS")
//	@KafkaListener(topics = {"${kafka.properties.topic-name.b}"}, groupId = "B-CONSUMERS")
//	@KafkaListener(topics = {"${kafka.properties.topic-name.c}"}, groupId = "C-CONSUMERS")
	@KafkaListener(topics = {"${kafka.properties.topic-name.a}", "${kafka.properties.topic-name.b}", "${kafka.properties.topic-name.c}"})
	public void receipt1(String message) {
		LOGGER.info(message);
	}
}
