package com.rahul.transactional.pattern.publisher;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${order.poller.topic.name}")
	private String topicName;
	
	public void published(String payload) {
CompletableFuture<SendResult<String,String>> future = kafkaTemplate.send(topicName ,payload); //topic name , message
		
		//future.get(); //It will block till the response come
		
		future.whenComplete((result,ex)->{
            if (ex == null) {
                System.out.println("Sent message=[" + payload +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        payload + "] due to : " + ex.getMessage());
            }
        });
	}
	
	
}
