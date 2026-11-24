package com.events.convert_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "convert_file_outbox")
@Getter
@Setter
public class ConvertFileOutbox{
	@Id
	@Column(name = "event_id")
	private UUID eventId;

	@Column(name = "event_type", nullable = false, length = 100)
	private String eventType;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "payload", nullable = false, columnDefinition = "jsonb")
	private String payload;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt =  LocalDateTime.now();

	@Column(name = "published_at")
	private LocalDateTime publishedAt;
}