package org.dk.tim.temporaytools;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.dk.tim.file.FileTool;

/**
 * --TEMPORARY TOOL-- HAS NOTHING TO DO WITH THE PROJECT
 * 	Renames all srt files to match their avi file
 *	Lists all avi files in a folder
 *  Lists all srt files
 *  
 *  Takes number one in each list and makes their name match
 *
 */
public class FileRenamer_RenameSrtFiles {
	private static FileTool fileTool = new FileTool();

	public static void main(String[] args) {
		System.out.println("Begin");
		List<File> listFilesInDirectory = fileTool.listFilesInDirectory(Paths.get("c:\\A"));
		List<File> srtFiles = new ArrayList<File>();
		List<File> aviFiles = new ArrayList<File>();

		for (File file : listFilesInDirectory) {
			String name = file.getName();
			if (name.indexOf("srt") > -1) {
				srtFiles.add(file);
			} else {
				aviFiles.add(file);
			}

		}

		if (srtFiles.size() != aviFiles.size()) {
			throw new IllegalArgumentException("Lists sizes does not match: Avi: " + aviFiles.size() + " Srt: " + srtFiles.size());
		}

		for (int i = 0; i < srtFiles.size(); i++) {
			File srtFile = srtFiles.get(i);
			File aviFile = aviFiles.get(i);

			String newName = aviFile.getName().replace(".mkv", ".srt");
			logAndRename(srtFile, newName);

		}

		System.out.println("DONE");
	}

	private static void logAndRename(File srtFile, String newName) {
		File file2 = new File(srtFile.getParentFile().getPath() + "\\" + newName);
		System.out.println("Renaming: " + srtFile.getName() + "|" + newName);
		boolean success = fileTool.renameFile(srtFile, file2);
		System.out.println(": " + success);
	}
}
