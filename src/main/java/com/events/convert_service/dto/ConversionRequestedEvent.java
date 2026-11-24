package com.events.convert_service.dto;

import java.util.UUID;

public record ConversionRequestedEvent(
			UUID eventId,
			String bucket,
			String key
			) {
}