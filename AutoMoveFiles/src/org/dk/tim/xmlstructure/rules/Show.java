package org.dk.tim.xmlstructure.rules;

import javax.xml.bind.annotation.XmlElement;

public class Show {
	@XmlElement
	private String Pattern;
	@XmlElement
	private String Path;
	@XmlElement
	private String PutInSeasons;

	@Override
	public String toString() {
		return String.format("Show [Pattern=%s, Path=%s, PutInSeasons=%s]", Pattern, Path, PutInSeasons);
	}

	//Getters and setters
	public String getPattern() {
		return Pattern;
	}

	public String getPath() {
		return Path;
	}

	public boolean isPutInSeasons() {
		return "true".equals(PutInSeasons);
	}

}
