package com.events.convert_service.service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class MinioService {
	private final MinioClient minioClient;

	private static final String BUCKET_NAME = "test";

	public void upload(Path file, String key) throws MinioException, IOException {
		boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
		if (!exists) {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
		}

		try (InputStream inputStream = Files.newInputStream(file)) {
			minioClient.putObject(
						PutObjectArgs.builder()
									.bucket(BUCKET_NAME)
									.object(key)
									.stream(inputStream, Files.size(file), -1L)
									.contentType("application/pdf")
									.build());
		}
	}

	public InputStream download(String key) throws MinioException {
		return minioClient.getObject(
					GetObjectArgs.builder()
								.bucket(BUCKET_NAME)
								.object(key)
								.build());
	}
}