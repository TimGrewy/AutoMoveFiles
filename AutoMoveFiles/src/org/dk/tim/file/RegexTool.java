package org.dk.tim.file;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTool {
	private static final String REGEX_SEASON_AND_EPISODE_GROUPING = ".*S(?<season>\\d{1,2})E(?<episode>\\d{1,2}).*";
	private static final String REGEX_COMBINED_SEASON_AND_EPISODE_GROUPING = ".*(?<combined>\\d{4}).*"; //Eg. 0101<->S01E01 

	public static String extractSeasonFromFileName(String fileName) {
		Pattern pattern = Pattern.compile(REGEX_SEASON_AND_EPISODE_GROUPING, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fileName);

		if (matcher.matches()) {
			return matcher.group("season");
		}
		return null;
	}

	public static String generateCorrectFileName(String fileName) {
		Pattern pattern = Pattern.compile(REGEX_COMBINED_SEASON_AND_EPISODE_GROUPING, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fileName);

		if (matcher.matches()) {
			String oldNotation = matcher.group("combined");
			String newSeasonAndEpisodeNotation = "S" + oldNotation.substring(0, 2) + "E" + oldNotation.substring(2, 4);

			return fileName.replace(oldNotation, newSeasonAndEpisodeNotation);
		}
		return null;
	}
}
