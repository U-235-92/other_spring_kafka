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
	@Value("${kafka.properties.topic-name.fun}")
	private String funTopicName;
	@Value("${kafka.properties.topic-name.easy}")
	private String easyTopicName;
	
	@Bean
	KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);
		return kafkaAdmin;
	}
	
	@Bean
	NewTopic funTopic() {
		return TopicBuilder.name(funTopicName).partitions(10).replicas(1).build();
	}
	
	@Bean
	NewTopic easyTopic() {
		return TopicBuilder.name(easyTopicName).partitions(10).replicas(1).build();
	}
}
