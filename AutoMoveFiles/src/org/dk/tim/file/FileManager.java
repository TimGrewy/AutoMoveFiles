package org.dk.tim.file;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

import org.dk.tim.log.Logger;
import org.dk.tim.xmlstructure.initialsetup.Properties;
import org.dk.tim.xmlstructure.rules.Show;

public class FileManager {
	private static final String FOLDER_NAME_FOR_SEASON = "Season";
	private Properties properties;
	private FileTool fileTool;

	public FileManager(Properties properties) {
		this.properties = properties;
		fileTool = new FileTool();
	}

	public void moveFile(File file, Show rule) {
		try {
			String path = rule.getPath();

			validateShowFolderExists(path);

			path = ensurePathEndsWithBackslash(path);

			File destinationFolder;
			if (rule.isPutInSeasons()) {
				file = applyFileNameFix(file);

				String season = RegexTool.extractSeasonFromFileName(file.getName());
				String seasonFolderName = FOLDER_NAME_FOR_SEASON + " " + season;
				destinationFolder = new File(path + seasonFolderName);
				createDestinationFolderOnDiskIfNotPresent(destinationFolder);
			} else {
				destinationFolder = new File(path);
			}

			fileTool.moveFile(file, destinationFolder);
			Logger.log(String.format("File %s moved to %s", file.getName(), destinationFolder));
		} catch (FileAlreadyExistsException e) {
			if (properties.isDeleteIfExists()) {
				fileTool.deleteFile(file);
				Logger.log(String.format("Deleted file %s", file.getName()));
			}
		}
	}

	/**
	 * Looks for tv-shows with the wrong notation of season and epsode (0101) and renames the file to S01E01
	 * @return 
	 */
	private File applyFileNameFix(File file) {
		String fileName = file.getName();
		String newFileName = RegexTool.generateCorrectFileName(fileName);
		String newFilePath = file.getParent() + "\\" + newFileName;
		File newFile = new File(newFilePath);
		fileTool.renameFile(file, newFile);
		return newFile;
	}

	/**
	 * Throws IllegalArgumentException if show folder does not exists.
	 * Business rule to avoid wrong shows beeing moved to newly created folders 
	 */
	private void validateShowFolderExists(String path) {
		File showFolder = new File(path);
		if (!showFolder.exists()) {
			throw new IllegalArgumentException(String.format("Show folder %s does not exists. Cannot continue.", showFolder));
		}
	}

	private String ensurePathEndsWithBackslash(String path) {
		if (!path.endsWith("\\")) {
			path = path + "\\";
		}
		return path;
	}

	private void createDestinationFolderOnDiskIfNotPresent(File destinationFolder) {
		if (!destinationFolder.exists()) {
			destinationFolder.mkdir();
			Logger.log("Created new season folder: " + destinationFolder);
		}
	}
}
