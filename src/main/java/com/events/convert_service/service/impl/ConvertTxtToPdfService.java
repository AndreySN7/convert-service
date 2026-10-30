package com.events.convert_service.service.impl;

import com.events.convert_service.model.FileType;
import com.events.convert_service.service.Converter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class ConvertTxtToPdfService implements Converter {
	@Value("${app.dir-output}")
	private String pathOut;

	@Override
	public Path convert(Path path) throws IOException, DocumentException {
		Path out = Path.of(pathOut, path.getFileName().toString().replace(".txt", ".pdf"));

		Document pdfDoc = new Document(PageSize.A4);
		PdfWriter.getInstance(pdfDoc, new FileOutputStream(out.toFile()))
					.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
		pdfDoc.open();

		BaseFont baseFont = BaseFont.createFont(
					"src/main/resources/fonts/Liberation Sans.ttf",
					BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
		Font myfont = new Font(baseFont, 11, Font.NORMAL);
		pdfDoc.add(new Paragraph("\n"));

		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String strLine;
			while ((strLine = br.readLine()) != null) {
				Paragraph para = new Paragraph(strLine + "\n", myfont);
				para.setAlignment(Element.ALIGN_JUSTIFIED);
				pdfDoc.add(para);
			}
		}
		pdfDoc.close();

		log.info("Converting TXT to PDF successfully");
		return out;
	}

	@Override
	public Boolean supports(FileType fileType) {
		return fileType.equals(FileType.TXT);
	}
}
