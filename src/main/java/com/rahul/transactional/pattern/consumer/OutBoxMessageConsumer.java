package com.rahul.transactional.pattern.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OutBoxMessageConsumer {

	@KafkaListener(topics ="unprocessed-order-events" , groupId ="transaction-outbox")
	public void consume(String payload) {
		log.info("Event consumed {} " , payload);
		
	}
}
