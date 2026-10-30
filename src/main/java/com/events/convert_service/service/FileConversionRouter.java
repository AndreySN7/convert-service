package com.events.convert_service.service;

import com.events.convert_service.model.FileType;
import com.itextpdf.text.DocumentException;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
@RequiredArgsConstructor
public class FileConversionRouter {
	private final MinioService minioService;
	private final ConvertServiceDispatcher dispatcher;

	@Value("${app.dir-input}")
	private String pathIn;

	@Scheduled(fixedRate = 50000)
	public void route() throws MinioException, IOException, DocumentException {

		String bucketName = "test";
//		String key = "photo.jpg";
//		String key = "photo.png";
		String key = "photo.txt";
//		String key = "images.zip";


		try (InputStream inputFile = minioService.download(key, bucketName)) {

			String extension = key.substring(key.lastIndexOf('.') + 1);
			FileType fileType = FileType.fromExtension(extension);
			Path tempFilePathIn = Files.createTempFile(Path.of(pathIn), extension + "ToPdf_", "." + extension);
			Files.copy(inputFile, tempFilePathIn, StandardCopyOption.REPLACE_EXISTING);

			Converter converter = dispatcher.resolve(fileType);
			Path tempFilePathOut = converter.convert(tempFilePathIn);

			StringBuilder newKey = new StringBuilder(key);
			newKey.insert(key.lastIndexOf('/') + 1, "pdf/")
						.replace(newKey.lastIndexOf("."), newKey.length() - extension.length(), "_")
						.append(".pdf");

			minioService.upload(tempFilePathOut, newKey.toString(), bucketName);
		}
	}
}
