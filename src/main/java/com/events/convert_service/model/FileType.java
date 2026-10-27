package com.events.convert_service.model;

import com.events.convert_service.exception.UnsupportedFileTypeException;

import java.util.Locale;

public enum FileType {
	TXT("txt"),
	PNG("png"),
	JPG("jpg"),
	ZIP("zip");

	private final String extension;

	FileType(String extension) {
		this.extension = extension;
	}

	public static FileType fromExtension(String extension) {
		for (FileType fileType : FileType.values()) {
			if (fileType.extension.equals(extension.toLowerCase(Locale.ROOT))) {
				return fileType;
			}
		}
		throw new UnsupportedFileTypeException("FileType not supported: " + extension);
	}
}
