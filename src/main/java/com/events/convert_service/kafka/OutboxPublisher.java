package com.events.convert_service.kafka;

import com.events.convert_service.entity.ConvertFileOutbox;
import com.events.convert_service.repository.ConvertFileOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
	private final ConvertFileOutboxRepository convertFileOutboxRepository;
	private final KafkaMessageSender kafkaMessageSender;

	@Value(value = "${app.batch-size-publish}")
	private int limit;

	@Scheduled(fixedDelayString = "${app.delay-outbox-publish}")
	public void publishBatch() throws ExecutionException, InterruptedException {
		List<ConvertFileOutbox> convertedFiles =
					convertFileOutboxRepository.findByPublishedAtIsNullOrderByCreatedAtAsc(PageRequest.of(0, limit));

		if (convertedFiles.isEmpty()) {
			return;
		}
		List<UUID> ids = new ArrayList<>();
		for (ConvertFileOutbox convertedFile : convertedFiles) {
			kafkaMessageSender.sendMessage(convertedFile.getEventId().toString(), convertedFile.getPayload());
			ids.add(convertedFile.getEventId());
		}
		convertFileOutboxRepository.updatePublishedAt(ids, LocalDateTime.now());
	}
}