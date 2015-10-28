package org.dk.tim.file;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTool {
	private static final String REGEX_SEASON_AND_EPISODE_GROUPING = ".*S(?<season>\\d{1,2})E(?<episode>\\d{1,2}).*";
	private static final String REGEX_COMBINED_SEASON_AND_EPISODE_GROUPING_4_DIGITS = ".*(?<combined>\\d{4}).*"; //Eg. 0101<->S01E01 
	private static final String REGEX_COMBINED_SEASON_AND_EPISODE_GROUPING_3_DIGITS = ".*(?<combined>\\d{3}).*"; //Eg. 101<->S01E01 

	public static String extractSeasonFromFileName(String fileName) {
		Pattern pattern = Pattern.compile(REGEX_SEASON_AND_EPISODE_GROUPING, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fileName);

		if (matcher.matches()) {
			return matcher.group("season");
		}
		return null;
	}

	/**
	 *	Looks for files names 0101 OR 101 and changes them to s01e01 
	 */
	public static String generateCorrectFileName(String fileName) {
		//Look for 0101
		Pattern pattern1 = Pattern.compile(REGEX_COMBINED_SEASON_AND_EPISODE_GROUPING_4_DIGITS, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = pattern1.matcher(fileName);

		if (matcher1.matches()) {
			String oldNotation = matcher1.group("combined");
			String newSeasonAndEpisodeNotation = "S" + oldNotation.substring(0, 2) + "E" + oldNotation.substring(2, 4);

			return fileName.replace(oldNotation, newSeasonAndEpisodeNotation);
		}
		//look for 101
		Pattern pattern2 = Pattern.compile(REGEX_COMBINED_SEASON_AND_EPISODE_GROUPING_3_DIGITS, Pattern.CASE_INSENSITIVE);
		Matcher matcher2 = pattern2.matcher(fileName);

		if (matcher2.matches()) {
			String oldNotation = matcher2.group("combined");
			if (numberNotInExclutionList(oldNotation)) {
				String newSeasonAndEpisodeNotation = "S0" + oldNotation.substring(0, 1) + "E" + oldNotation.substring(1, 3);

				return fileName.replace(oldNotation, newSeasonAndEpisodeNotation);
			}
		}
		return null;
	}

	private static boolean numberNotInExclutionList(String oldNotation) {
		List<String> exclutions = Arrays.asList("2014");
		return !exclutions.contains(oldNotation);
	}
}
