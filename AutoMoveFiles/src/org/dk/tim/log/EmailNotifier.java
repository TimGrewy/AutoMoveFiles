package org.dk.tim.log;

import java.io.IOException;

import org.dk.tim.common.StringUtils;
import org.dk.tim.xmlstructure.initialsetup.Properties;

public class EmailNotifier {
	private String jarLocation;
	private String configurationLocation;

	public EmailNotifier(Properties properties) {
		if (properties != null) {
			this.jarLocation = properties.getEmailJarLocation();
			this.configurationLocation = properties.getEmailConfigurationLocation();
		}
	}

	public void sendNotificationEmail(String to, String header, String body) {
		if (isSetup()) {
			String launchCommand = String.format("java -jar %s %s \"%s\" \"%s\" %s", jarLocation, to, header, body, configurationLocation);
			try {
				Process proc = Runtime.getRuntime().exec(launchCommand);
				proc.waitFor();
				java.io.InputStream is = proc.getInputStream();
				byte b[] = new byte[is.available()];
				is.read(b, 0, b.length);
				Logger.logToSystemLogAndSystemOut("Message from email jar: " + new String(b));
			} catch (IOException | InterruptedException e) {
				Logger.logToSystemLogAndSystemOut("Failed to send email! Command: " + launchCommand + "Error: " + e.getMessage());
			}
		} else {
			Logger.logToSystemLogAndSystemOut("Email has not been set up, skipping sending email");
		}
	}

	private boolean isSetup() {
		return StringUtils.isNotEmpty(jarLocation) && StringUtils.isNotEmpty(configurationLocation);
	}
}
