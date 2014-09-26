package org.dk.tim.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public void moveFiles(List<File> filesInDirectory) {
		for (File file : filesInDirectory) {
			if (file.isDirectory()) {
				Path path = Paths.get(file.getAbsolutePath());
				Logger.log("Processing directory: " + path);
				List<File> filesInDirectory2 = fileTool.listFilesInDirectory(path);
				moveFiles(filesInDirectory2);
				fileTool.deleteFile(file);
			} else {
				processFile(file);
			}
		}
	}

	private void processFile(File file) {
		Show rule = findMatchingRule(file);
		if (rule != null) {
			fileManager.moveFile(file, rule);
		}
	}

	private Show findMatchingRule(File file) {
		for (Show rule : rules.getRules()) {
			String pattern = rule.getPattern();

			if (matches(file.getName(), pattern)) {
				Logger.log(String.format("File %s matched rule %s", file.getName(), rule.getPattern()));
				return rule;
			} else {
				Logger.log(String.format("File %s did not matched rule %s", file.getName(), rule.getPattern()));
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
