package com.other.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TopicConfiguration {

	@Value("${kafka.properties.bootstrap-server}")
	private String bootstrapServer;
	@Value("${kafka.properties.topic-name.a}")
	private String aTopicName;
	@Value("${kafka.properties.topic-name.b}")
	private String bTopicName;
	@Value("${kafka.properties.topic-name.c}")
	private String cTopicName;
	
	@Bean
	KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);
		return kafkaAdmin;
	}
	
	@Bean
	NewTopic aTopic() {
		return TopicBuilder.name(aTopicName).partitions(3).replicas(1).build();
	}
	
	@Bean
	NewTopic bTopic() {
		return TopicBuilder.name(bTopicName).partitions(3).replicas(1).build();
	}
	
	@Bean
	NewTopic cTopic() {
		return TopicBuilder.name(cTopicName).partitions(3).replicas(1).build();
	}
}
