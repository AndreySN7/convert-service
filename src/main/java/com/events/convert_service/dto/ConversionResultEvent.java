package com.events.convert_service.dto;

import org.jspecify.annotations.Nullable;

import java.util.UUID;

public record ConversionResultEvent(
			UUID eventId,
			@Nullable String bucket,
			@Nullable String key,
			Status status,
			@Nullable String errorMessage
) {
	public enum Status {SUCCESS, FAILED }
}
