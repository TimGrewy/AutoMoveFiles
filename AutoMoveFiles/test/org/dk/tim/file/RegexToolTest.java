package org.dk.tim.file;

//import static org.junit.Assert.*;

//import org.junit.Test; Dang, no junit on path. Nevermind then...

public class RegexToolTest {

	public static void main(String[] args) {
		String family = "family.guy.1318.hdtv-lol.mp4";
		String fileName = RegexTool.generateCorrectFileName(family);
		System.out.println(fileName);

		String family1 = "family.guy.118.hdtv-lol.mp4";
		String fileName1 = RegexTool.generateCorrectFileName(family1);
		System.out.println(fileName1);
	}

}
