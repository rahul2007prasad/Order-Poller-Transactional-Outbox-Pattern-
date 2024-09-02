package com.rahul.transactional.pattern.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

	@Value("${order.poller.topic.name}")
	private String topicName;
	
	@Bean
	public NewTopic createTopic() {
		return new NewTopic(topicName, 3, (short) 1);// topic name,num of partitions, replication factor

	}
}
