package com.events.convert_service.service;

import com.events.convert_service.model.FileType;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.nio.file.Path;

public interface Converter {
	Path convert(Path path) throws IOException, DocumentException;

	Boolean supports(FileType fileType);
}
