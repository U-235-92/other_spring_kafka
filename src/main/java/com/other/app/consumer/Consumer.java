package com.other.app.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());
	
	@SuppressWarnings("unused")
	private ExecutorService executor = Executors.newCachedThreadPool(); 
	
	@KafkaListener(topics = "${kafka.properties.topic-name.fun}", concurrency = "3", groupId = "fun-consumers")
	@KafkaListener(topics = "${kafka.properties.topic-name.easy}", concurrency = "3", groupId = "easy-consumers")
	public void receipt(String message) {
		LOGGER.info(message);
	}
	
//	@KafkaListener(topics = {"${kafka.properties.topic-name.fun}", "${kafka.properties.topic-name.easy}"}, concurrency = "2")
//	public void receipt(String message) {
//		LOGGER.info(message);
//	}
	
//	@KafkaListener(topics = "${kafka.properties.topic-name}", groupId = "fun-1", concurrency = "1")
//	public void receipt1(String message) {
//		executor.submit(() -> LOGGER.info(message));
//	}
	
//	@KafkaListener(topics = "${kafka.properties.topic-name}", groupId = "fun-1", concurrency = "1")
//	@KafkaListener(topics = "${kafka.properties.topic-name}", groupId = "fun-2")
//	public void receipt2(String message) {
//		LOGGER.info(message);
//	}
}
