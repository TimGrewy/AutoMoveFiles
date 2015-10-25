package org.dk.tim.file.exception;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class VideoFileNotMatchedException extends RuntimeException {
	private static List<String> videoExtension = Arrays.asList(".avi", ".mkv", ".mp4");

	public static boolean isFileVideoFile(File file) {
		for (String string : videoExtension) {
			if (file.getName().endsWith(string)) {
				return true;
			}
		}
		return false;
	}

	public VideoFileNotMatchedException(File file) {
		super(String.format("File %s is a video file (ie. %s) - These file should always be matched or a rule is missing!", file, videoExtension));
	}
}
