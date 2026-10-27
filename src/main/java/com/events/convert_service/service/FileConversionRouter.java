package com.events.convert_service.service;

import com.events.convert_service.model.FileType;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@AllArgsConstructor
public class FileConversionRouter {
	private final MinioService minioService;
	private final ConvertServiceDispatcher dispatcher;

	@Scheduled(cron = "0 * * * * *")
	public void route() throws MinioException, IOException {

		String key = "photo.jpg";
//		String key = "photo.png";
//		String key = "photo.txt";
//		String key = "images.zip";
		try (InputStream inputFile = minioService.download(key)) {


//		String fileName = "";
			String extension = key.substring(key.lastIndexOf('.') + 1);
			FileType fileType = FileType.fromExtension(extension);

			Converter converter = dispatcher.resolve(fileType);
			converter.convert();
		}
	}
}
