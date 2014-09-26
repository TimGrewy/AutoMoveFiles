package org.dk.tim.program;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.dk.tim.file.FileTool;
import org.dk.tim.file.ReadFile;
import org.dk.tim.file.RuleMatcher;
import org.dk.tim.log.Logger;
import org.dk.tim.parsexml.XMLParser;
import org.dk.tim.xmlstructure.initialsetup.Properties;
import org.dk.tim.xmlstructure.rules.Rules;

public class AutoFileMover {
	private XMLParser xmlParser;
	private ReadFile readFile;
	private FileTool fileTool;
	private RuleMatcher ruleMatcher;

	public AutoFileMover() {
		fileTool = new FileTool();
		xmlParser = new XMLParser();
		readFile = new ReadFile();
	}

	public void executeProgram(Path xmlFile) {
		Properties properties = initializeProperties(xmlFile);
		setupLogging(properties.getLogFile());
		Logger.log("Stating program");

		Rules rules = getRules(properties);
		ruleMatcher = new RuleMatcher(properties, rules);

		Path sourceDirectory = Paths.get(properties.getSourceFolder());
		moveFiles(sourceDirectory);
	}

	private void setupLogging(String logFile) {
		System.out.println("Setting up logger: " + logFile);
		Logger.instance = new Logger(logFile);
	}

	private void moveFiles(Path sourceDirectory) {
		List<File> filesInDirectory = fileTool.listFilesInDirectory(sourceDirectory);
		ruleMatcher.moveFiles(filesInDirectory);
	}

	private Rules getRules(Properties properties) {
		Path PatternsFilePath = Paths.get(properties.getPatternsFile());
		String xml = readFile.getFileContent(PatternsFilePath);
		Rules rules = xmlParser.parseRules(xml);
		return rules;
	}

	private Properties initializeProperties(Path xmlFile) {
		String xml = readFile.getFileContent(xmlFile);
		Properties properties = xmlParser.parseProperties(xml);
		return properties;
	}
}
