package org.dk.tim.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

	public void deleteFile(File file) {
		try {
			Path source = Paths.get(file.getPath());
			if (file.isDirectory()) {
				Path path = Paths.get(file.getAbsolutePath());
				List<File> filesInDirectory2 = listFilesInDirectory(path);
				for (File file2 : filesInDirectory2) {
					deleteFile(file2);
				}
			}
			Files.delete(source);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void copyFile(File file, String destinationPath) {
		try {
			Path source = Paths.get(file.getPath());
			Path target = Paths.get(destinationPath);
			Files.copy(source, target);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void moveFile(File file, File destinationFolder) throws FileAlreadyExistsException {
		try {
			Path source = Paths.get(file.getPath());
			Path target = Paths.get(destinationFolder + "\\" + file.getName());
			Files.move(source, target);
		} catch (FileAlreadyExistsException e) {
			throw e;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<File> listFilesInDirectory(Path sourceDirectory) {
		List<File> result = new ArrayList<File>();
		File folder = new File(sourceDirectory.toString());
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			result.add(file);
		}
		return result;
	}

	public long getFileByteSize(File file) {
		return file.length();
	}

	public boolean renameFile(File file, File newFile) {
		return file.renameTo(newFile);
	}
}