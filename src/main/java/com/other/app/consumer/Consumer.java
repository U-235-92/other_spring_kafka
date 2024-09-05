package com.other.app.consumer;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());
	
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
						return logRecord.getMessage();
					}
				};
				super.setFormatter(formatter);
			}
		});
		LOGGER.setLevel(Level.INFO);
	}
	
	@KafkaListener(topics = "${kafka.properties.topic-name}")
	public void receipt1(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition, @Header(KafkaHeaders.GROUP_ID) String groupId) {
		Method method = getMethod("receipt1", String.class, String.class, int.class, String.class);
		printReport(method, message, topic, partition, groupId);
		sleep(1);
	}
	
	@KafkaListener(topics = "${kafka.properties.topic-name}")
	public void receipt2(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition, @Header(KafkaHeaders.GROUP_ID) String groupId) {
		Method method = getMethod("receipt2", String.class, String.class, int.class, String.class);
		printReport(method, message, topic, partition, groupId);
		sleep(1);
	}
	
	private Method getMethod(String string, Class<?>... classes) {
		Method method = null;
		try {
			 method = Consumer.class.getDeclaredMethod(string, classes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return method;
	}

	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void printReport(Method method, String message, String topic, int partition, String groupId) {
		String report = "[" + method.getName() +  "] receipted a message: " + message + " Topic: " + topic + " Partition: " + partition + " Group Id: " + groupId;
		LOGGER.info(report);
	}
}
