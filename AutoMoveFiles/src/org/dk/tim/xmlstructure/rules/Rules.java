package org.dk.tim.xmlstructure.rules;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Rules")
public class Rules {

	 @XmlElement (name = "Rule")
	private List<Show> rules = new ArrayList<Show>();

	 public List<Show> getRules() {
		return rules;
	}

}
