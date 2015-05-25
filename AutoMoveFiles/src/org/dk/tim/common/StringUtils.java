package org.dk.tim.common;

public class StringUtils {

	public static boolean isEmpty(String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		}
		return false;
	}
}
