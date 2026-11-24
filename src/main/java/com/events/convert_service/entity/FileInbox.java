package com.events.convert_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "file_inbox")
@NoArgsConstructor
@Getter
@Setter
public class FileInbox {
	@Id
	@Column(name = "event_id")
	private UUID eventId;

	@Column(name = "processed_at",  nullable = false)
	private LocalDateTime processedAt = LocalDateTime.now();
}
