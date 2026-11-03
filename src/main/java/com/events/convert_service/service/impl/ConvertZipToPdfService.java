package com.events.convert_service.service.impl;

import com.events.convert_service.model.FileType;
import com.events.convert_service.service.ConvertServiceDispatcher;
import com.events.convert_service.service.Converter;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@Slf4j
public class ConvertZipToPdfService implements Converter {
	private final ConvertServiceDispatcher dispatcher;

	public ConvertZipToPdfService(@Lazy ConvertServiceDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Value("${app.dir-output}")
	private String pathOut;
	@Value("${app.dir-input}")
	private String pathIn;

	@Override
	public Path convert(Path path) throws IOException, DocumentException {
		Path out = Path.of(pathOut, path.getFileName().toString().replace(".zip", ".pdf"));

		try (ZipFile zipFile = ZipFile.builder().setPath(path).get()) {
			List<File> tempPdfFiles = new ArrayList<>();
			Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
			while (entries.hasMoreElements()) {
				ZipArchiveEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				try (InputStream in = zipFile.getInputStream(entry)) {
					String extension = entry.getName().substring(entry.getName().lastIndexOf('.') + 1);
					FileType type = FileType.fromExtension(extension);
					Path tempFilePathIn =
								Files.createTempFile(Path.of(pathIn), extension + "ToPdf_", "." + extension);
					Files.copy(in, tempFilePathIn, StandardCopyOption.REPLACE_EXISTING);

					Path entryPdf = dispatcher.resolve(type).convert(tempFilePathIn);
					tempPdfFiles.add(entryPdf.toFile());
					log.info("entry pdf: {}", entryPdf);
				}
			}
			PDFMergerUtility merger = new PDFMergerUtility();
			merger.setDestinationFileName(out.toString());
			for (File file : tempPdfFiles) {
				merger.addSource(file);
			}
			merger.mergeDocuments(null);
		}

		log.info("Converting ZIP to PDF successfully");
		return out;
	}

	@Override
	public Boolean supports(FileType fileType) {
		return fileType.equals(FileType.ZIP);
	}
}
