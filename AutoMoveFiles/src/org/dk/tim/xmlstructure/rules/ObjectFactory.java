package org.dk.tim.xmlstructure.rules;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	public Rules createProperties() {
		return new Rules();
	}
}