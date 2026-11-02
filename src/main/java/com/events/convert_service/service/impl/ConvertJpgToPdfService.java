package com.events.convert_service.service.impl;

import com.events.convert_service.model.FileType;
import com.events.convert_service.service.Converter;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
@Slf4j
public class ConvertJpgToPdfService implements Converter {
	@Value("${app.dir-output}")
	private String pathOut;

	@Override
	public Path convert(Path path) throws IOException, DocumentException {
		Path out = Path.of(pathOut, path.getFileName().toString().replace(".jpg", ".pdf"));

		ConvertPngToPdfService.convertImage(path, out);

		log.info("Converting JPG to PDF successfully");
		return out;
	}

	@Override
	public Boolean supports(FileType fileType) {
		return fileType.equals(FileType.JPG);
	}
}
