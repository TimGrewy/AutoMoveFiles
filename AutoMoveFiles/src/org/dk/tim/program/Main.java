package org.dk.tim.program;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.dk.tim.log.Logger;

public class Main {
	/**
	 * Execute by command: java -jar AutoMoveFiles.jar
	 * @param args (override where settings.xml is located)
	 * Export: Eclipse->File->Export->java-jar - Choose Main.java to be default class
	 */
	public static void main(String[] args) {
		try {
			long start = System.currentTimeMillis();

			String settingsFile = extractSettingsFileLocation(args);
			Path settingsFilePath = Paths.get(settingsFile);

			AutoFileMover autoMoveFiles = new AutoFileMover();
			autoMoveFiles.executeProgram(settingsFilePath);

			long end = System.currentTimeMillis();
			System.out.println("Completed. Duration: " + (end - start) + " ms");
		} catch (Exception e) {
			System.out.println("Failed to run program. " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			Logger.closeLogger();
		}
	}

	private static String extractSettingsFileLocation(String[] args) {
		String settingsFile;
		if (args.length == 0) {
			String curDir = System.getProperty("user.dir");
			settingsFile = curDir + "//settings.xml";
		} else {
			settingsFile = args[0];
		}
		return settingsFile;
	}

}
