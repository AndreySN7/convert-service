package com.events.convert_service.service;

import com.events.convert_service.model.FileType;

public interface Converter {
	void convert();

	Boolean supports(FileType fileType);
}
