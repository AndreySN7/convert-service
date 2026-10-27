package com.events.convert_service.service;

import com.events.convert_service.exception.UnsupportedFileTypeException;
import com.events.convert_service.model.FileType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConvertServiceDispatcher {
	private final List<Converter> converters;

	public Converter resolve(FileType fileType) {
		return converters.stream()
					.filter(c -> c.supports(fileType))
					.findFirst()
					.orElseThrow(() -> new UnsupportedFileTypeException("FileType not supported: " + fileType));
	}
}
