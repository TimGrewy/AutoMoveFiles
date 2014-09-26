package org.dk.tim.file;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTool {
	private static final String REGEX_SEASON_AND_EPISODE_GROUPING = ".*S(?<season>\\d{1,2})E(?<episode>\\d{1,2}).*";

	public static String extractSeasonFromFileName(String sample) {
		Pattern pattern = Pattern.compile(REGEX_SEASON_AND_EPISODE_GROUPING, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sample);

		if (matcher.matches()) {
			return matcher.group("season");
		}
		return null;
	}
}
