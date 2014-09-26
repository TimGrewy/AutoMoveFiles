package org.dk.tim.xmlstructure.initialsetup;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Properties")
public class Properties {
	@XmlElement
	private String sourceFolder;
	@XmlElement
	private String patternsFile;
	@XmlElement
	private String logFile;
	@XmlElement
	private String deleteIfExists;
	@XmlElement
	private String deleteIfNoMatch;
	
	
	//Getters and Setters
	public String getSourceFolder() {
		if(sourceFolder == null){
			throw new IllegalArgumentException("No sourceFolder folder in settings XML file.");
		}
		return sourceFolder;
	}
	public String getPatternsFile() {
		return patternsFile;
	}
	public String getLogFile() {
		return logFile;
	}
	public boolean isDeleteIfExists() {
		return "true".equals(deleteIfExists);
	}
	public boolean isDeleteIfNoMatch() {
		return "true".equals(deleteIfNoMatch);
	}
}
