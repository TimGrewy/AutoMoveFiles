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
	public static void main(String[] args) {
		FileTool fileTool = new FileTool();
		List<File> listFilesInDirectory = fileTool.listFilesInDirectory(Paths.get("c:\\A\\Modern family"));
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

			String newName = srtFile.getName().replace(".srt", ".avi");
			File file2 = new File(aviFile.getParentFile().getPath() + "\\" + newName);
			System.out.print("Renaming: " + aviFile.getName() + "|" + newName);
			boolean success = fileTool.renameFile(aviFile, file2);
			System.out.println(": " + success);

		}

		System.out.println("DONE");
	}
}
