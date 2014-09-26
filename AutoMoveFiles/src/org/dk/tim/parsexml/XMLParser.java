package org.dk.tim.parsexml;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.dk.tim.xmlstructure.initialsetup.Properties;
import org.dk.tim.xmlstructure.rules.Rules;

public class XMLParser {

	public Properties parseProperties(String xml) {
		validateNotEmpty(xml);
		try {
			JAXBContext jc = JAXBContext.newInstance("org.dk.tim.xmlstructure.initialsetup");
			Unmarshaller um = jc.createUnmarshaller();
			StringReader sr = new StringReader(xml);
			return (Properties) um.unmarshal(sr);
		} catch (JAXBException e) {
			System.err.println("Exception parsing xml " + xml);
			throw new RuntimeException(e);
		}
	}

	public Rules parseRules(String xml) {
		validateNotEmpty(xml);
		try {
			JAXBContext jc = JAXBContext.newInstance("org.dk.tim.xmlstructure.rules");
			Unmarshaller um = jc.createUnmarshaller();
			StringReader sr = new StringReader(xml);
			return (Rules) um.unmarshal(sr);
		} catch (JAXBException e) {
			System.err.println("Exception parsing xml " + xml);
			throw new RuntimeException(e);
		}
	}

	private void validateNotEmpty(String xml) {
		if (xml == null || xml.length() == 0) {
			throw new IllegalArgumentException("XML query is empty!");
		}
	}
}
