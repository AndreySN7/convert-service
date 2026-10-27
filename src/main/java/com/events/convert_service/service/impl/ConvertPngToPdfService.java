package com.events.convert_service.service.impl;

import com.events.convert_service.model.FileType;
import com.events.convert_service.service.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConvertPngToPdfService implements Converter {
	@Override
	public void convert() {
		log.info("Converting PNG to PDF successfully");
	}

	@Override
	public Boolean supports(FileType fileType) {
		return fileType.equals(FileType.PNG);
	}
}
