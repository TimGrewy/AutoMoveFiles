package org.dk.tim.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dk.tim.file.exception.VideoFileNotMatchedException;
import org.dk.tim.log.Logger;
import org.dk.tim.xmlstructure.initialsetup.Properties;
import org.dk.tim.xmlstructure.rules.Rules;
import org.dk.tim.xmlstructure.rules.Show;

public class RuleMatcher {
	private Rules rules;
	private FileManager fileManager;
	private FileTool fileTool;

	public RuleMatcher(Properties properties, Rules rules) {
		validateNotEmptyRules(rules);

		this.rules = rules;
		fileManager = new FileManager(properties);
		fileTool = new FileTool();
	}

	/**
	 * Obs. Throws VideoFileNotMatchedException if a video file is not matched by a rule (Other files will be deleted)
	 */
	public void moveFiles(List<File> filesInDirectory) {
		for (File file : filesInDirectory) {
			if (file.isDirectory()) {
				try {
					processDirectory(file);
				} catch (VideoFileNotMatchedException e) {
					//A video file was found in this folder which does not match any rule - we skip this folder and continue with the next
					Logger.logToSystemLogAndSystemOut(e.getMessage());
				}
			} else if (file.getName().endsWith(".nfo") || file.getName().endsWith(".txt")) {
				Logger.logToSystemLogAndSystemOut("Deleting "+file.getName().substring(file.getName().lastIndexOf("."))+" file: " + file.getName());
				fileTool.deleteFile(file);
			} else {
				processFile(file);
			}
		}
	}

	private void processDirectory(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		Logger.logToSystemLogAndSystemOut("Processing directory: " + path);
		List<File> filesInDirectory2 = fileTool.listFilesInDirectory(path);
		moveFiles(filesInDirectory2);
		Logger.logToSystemLogAndSystemOut("Deleting file after moving: " + file.getName());
		fileTool.deleteFile(file);
	}

	private void processFile(File file) {
		if (isSampleFile(file)) {
			fileTool.deleteFile(file);
		}

		Show rule = findMatchingRule(file);
		if (rule != null) {
			fileManager.moveFile(file, rule);
		} else if (rule == null && VideoFileNotMatchedException.isFileVideoFile(file)) {
			throw new VideoFileNotMatchedException(file);
		}
	}

	/**
	 *	determines if a file is a sample file based on  if it contains "sample."
	 *  (We dont need these) 
	 */
	private boolean isSampleFile(File file) {
		return file.getName().contains("sample.");
	}

	private Show findMatchingRule(File file) {
		for (Show rule : rules.getRules()) {
			String pattern = rule.getPattern();

			if (matches(file.getName(), pattern)) {
				Logger.logToSystemLogAndSystemOut(String.format("File %s matched rule %s", file.getName(), rule.getPattern()));
				return rule;
			} else {
				Logger.logToSystemLogAndSystemOut(String.format("File %s did not matched rule %s", file.getName(), rule.getPattern()));
			}
		}

		return null;
	}

	private boolean matches(String target, String rawPattern) {
		Pattern pattern = Pattern.compile(rawPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(target);

		return matcher.matches();
	}

	private void validateNotEmptyRules(Rules rules) {
		if (rules.getRules().size() == 0) {
			throw new IllegalArgumentException("No rules was defined!");
		}
	}
}
