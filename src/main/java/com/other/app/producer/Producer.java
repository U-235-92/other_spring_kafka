package com.other.app.producer;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
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
	
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler() {
			@Override
			protected void setOutputStream(OutputStream out) throws SecurityException {
				super.setOutputStream(System.out);
			}
			
			@Override
			public void setFormatter(Formatter newFormatter) throws SecurityException {
				Formatter formatter = new Formatter() {
					@Override
					public String format(LogRecord logRecord) {
						return logRecord.getMessage() + "\n";
					}
				};
				super.setFormatter(formatter);
			}
		});
		LOGGER.setLevel(Level.OFF);
	}
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	@Value("${kafka.properties.topic-name.fun}")
	private String funTopic;
	@Value("${kafka.properties.topic-name.easy}")
	private String easyTopic;
	
	public void send(String message) {
		kafkaTemplate.send(funTopic, message);
		kafkaTemplate.send(easyTopic, message);
		kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
			@Override
			public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
				LOGGER.info("[SUCCESS SEND A MESSAGE] Topic: " + producerRecord.topic() + " Partition: " + producerRecord.partition());
			}
			
			@Override
			public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata,
					Exception exception) {
				LOGGER.warning("[ERROR OCCURED DURING SEND A MESSAGE]");
			}
		});
	}
}
