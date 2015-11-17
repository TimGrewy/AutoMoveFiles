package org.dk.tim.program;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.dk.tim.file.ReadFile;
import org.dk.tim.log.EmailNotifier;
import org.dk.tim.log.Logger;
import org.dk.tim.parsexml.XMLParser;
import org.dk.tim.xmlstructure.initialsetup.Properties;

public class Main {
	/**
	 * Execute by command: java -jar AutoMoveFiles.jar
	 * @param args (override where settings.xml is located)
	 * Export: Eclipse->File->Export->java-jar (Runnable jar)- Choose Main.java to be default class - make sure the main project folder is selected.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Properties properties = null;
		try {
			long start = System.currentTimeMillis();

			String settingsFile = extractSettingsFileLocation(args);
			Path settingsFilePath = Paths.get(settingsFile);

			AutoFileMover autoMoveFiles = new AutoFileMover();
			properties = initializeProperties(settingsFilePath);
			setupLogging(properties.getLogFile());

			autoMoveFiles.executeProgram(properties);

			long end = System.currentTimeMillis();
			System.out.println("Completed. Duration: " + (end - start) + " ms");
		} catch (Exception e) {
			String errorMsg = "Failed to run program. " + e.getMessage();
			System.out.println(errorMsg);
			Logger.logToSystemLog(errorMsg);
			new EmailNotifier(properties).sendNotificationEmail(properties.getErrorEmailSendTo(), "Failed to run @ " + new Date(), errorMsg);
			e.printStackTrace();
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

	private static Properties initializeProperties(Path xmlFile) {
		ReadFile readFile = new ReadFile();
		XMLParser xmlParser = new XMLParser();
		String xml = readFile.getFileContent(xmlFile);
		Properties properties = xmlParser.parseProperties(xml);
		return properties;
	}

	private static void setupLogging(String logFile) {
		System.out.println("Setting up logger: " + logFile);
		Logger.systemLog = new Logger(logFile);
	}

}
