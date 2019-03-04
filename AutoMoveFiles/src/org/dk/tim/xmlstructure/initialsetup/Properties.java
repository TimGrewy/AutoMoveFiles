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
	private String fileToolLog;
	@XmlElement
	private String deleteIfExists;
	@XmlElement
	private String deleteIfNoMatch;
	private String emailJarLocation;
	private String emailConfigurationLocation;
	private String errorEmailSendTo;

	//Getters and Setters
	public String getSourceFolder() {
		if (sourceFolder == null) {
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
	public String getFileToolLog() {
		return logFile;
	}

	public boolean isDeleteIfExists() {
		return "true".equals(deleteIfExists);
	}

	public boolean isDeleteIfNoMatch() {
		return "true".equals(deleteIfNoMatch);
	}

	public String getEmailJarLocation() {
		return emailJarLocation;
	}

	public void setEmailJarLocation(String emailJarLocation) {
		this.emailJarLocation = emailJarLocation;
	}

	public String getEmailConfigurationLocation() {
		return emailConfigurationLocation;
	}

	public void setEmailConfigurationLocation(String emailConfigurationLocation) {
		this.emailConfigurationLocation = emailConfigurationLocation;
	}

	public String getErrorEmailSendTo() {
		return errorEmailSendTo;
	}

	public void setErrorEmailSendTo(String errorEmailSendTo) {
		this.errorEmailSendTo = errorEmailSendTo;
	}
}