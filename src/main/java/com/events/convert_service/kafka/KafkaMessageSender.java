package com.events.convert_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.topics.topic-converted}")
	private String topicConverted;

	public void sendMessage(String key, String payload) throws ExecutionException, InterruptedException {
		kafkaTemplate.send(topicConverted, key, payload).get();
	}
}