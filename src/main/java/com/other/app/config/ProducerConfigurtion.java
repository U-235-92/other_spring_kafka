package com.other.app.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;

@Configuration
@EnableKafka
public class ProducerConfigurtion {

	@Value("${kafka.properties.bootstrap-server}")
	private String bootstrapServer;
	@Value("${kafka.properties.producer-client-id}")
	private String clientId;
	@Value("${kafka.properties.topic-name.a}")
	private String aTopic;
	@Value("${kafka.properties.topic-name.b}")
	private String bTopic;
	@Value("${kafka.properties.topic-name.c}")
	private String cTopic;
	
	@Bean
	Map<String, Object> producerProperties() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
	
	@Bean
	ProducerFactory<String, String> producerFactory() {
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(producerProperties());
		return producerFactory;
	}
	
	@Bean
	RoutingKafkaTemplate kafkaTemplate(GenericApplicationContext applicationContext) {
		ProducerFactory<Object, Object> aProducerFactory = new DefaultKafkaProducerFactory<>(producerProperties());
		ProducerFactory<Object, Object> bProducerFactory = new DefaultKafkaProducerFactory<>(producerProperties());
		ProducerFactory<Object, Object> cProducerFactory = new DefaultKafkaProducerFactory<>(producerProperties());
		applicationContext.getBeanFactory().registerSingleton(aTopic, aProducerFactory);
		applicationContext.getBeanFactory().registerSingleton(bTopic, bProducerFactory);
		applicationContext.getBeanFactory().registerSingleton(cTopic, cProducerFactory);
		Map<Pattern, ProducerFactory<Object, Object>> routingKafkaTemplateConfig = new LinkedHashMap<>();
		routingKafkaTemplateConfig.put(Pattern.compile(aTopic), aProducerFactory);
		routingKafkaTemplateConfig.put(Pattern.compile(bTopic), bProducerFactory);
		routingKafkaTemplateConfig.put(Pattern.compile(cTopic), cProducerFactory);
		RoutingKafkaTemplate routingKafkaTemplate = new RoutingKafkaTemplate(routingKafkaTemplateConfig);
		return routingKafkaTemplate;
	}
}
