package com.events.convert_service.service.impl;

import com.events.convert_service.model.FileType;
import com.events.convert_service.service.Converter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
@Slf4j
public class ConvertPngToPdfService implements Converter {
	@Value("${app.dir-output}")
	private String pathOut;

	@Override
	public Path convert(Path path) throws IOException, DocumentException {
		Path out = Path.of(pathOut, path.getFileName().toString().replace(".png", ".pdf"));

		convertImage(path, out);

		log.info("Converting PNG to PDF successfully");
		return out;
	}

	static void convertImage(Path path, Path out) throws IOException, DocumentException {
		try (FileOutputStream fos = new FileOutputStream(out.toFile())) {

			Document pdfDoc = new Document(PageSize.A4);
			PdfWriter.getInstance(pdfDoc, fos);
			pdfDoc.open();

			float availableWidth = PageSize.A4.getWidth() - 2 * 36;
			float availableHeight = PageSize.A4.getHeight() - 2 * 36;
			Image image = Image.getInstance(path.toFile().getAbsolutePath());
			image.scaleToFit(availableWidth, availableHeight);

			pdfDoc.add(image);
			pdfDoc.close();
		}
	}

	@Override
	public Boolean supports(FileType fileType) {
		return fileType.equals(FileType.PNG);
	}
}
