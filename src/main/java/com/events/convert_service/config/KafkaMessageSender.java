package com.events.convert_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.topics.topic-converted}")
	private String topicConverted;

	public void sendMessage(String message) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicConverted, message);
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
			} else {
				log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
			}
		});
	}
}