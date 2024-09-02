package com.rahul.transactional.pattern.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rahul.transactional.pattern.entity.Outbox;
import com.rahul.transactional.pattern.publisher.MessagePublisher;
import com.rahul.transactional.pattern.repo.OutBoxRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@Slf4j
public class OrderPollerService {

	@Autowired
	private OutBoxRepo outBoxRepo;
	
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	
	@Scheduled(fixedRate = 60000)
	public void pollOutboxMessgesAndPublish() {
		
		// 1. fethc unprocessd recors
		
		List<Outbox> unprocessdRecords =  outBoxRepo.findByProcessed(false);
		log.info("unprocessed record count : {}" , unprocessdRecords.size());
		
		//2. publish to kfafka
		
		unprocessdRecords.forEach(
				outbox ->{
					try {
						messagePublisher.published(outbox.getPayload());
						outbox.setProcessed(true);
						outBoxRepo.save(outbox);
					}catch (Exception e) {
						log.error(e.getMessage());
					}
				}
				);
		
		
	}
}
