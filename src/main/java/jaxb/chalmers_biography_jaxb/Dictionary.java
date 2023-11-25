package jaxb.chalmers_biography_jaxb;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dictionary {
	@XmlElement(name = "entry")
	private List<Entry> entries;

	@Override
	public String toString() {
		return "Dictionary [entries=" + entries + "]";
	}
}

@XmlAccessorType(XmlAccessType.FIELD)
class Entry {
	@XmlAttribute
	private String birthplace;
	@XmlAttribute
	private String id;
	@XmlAttribute
	private int born;
	@XmlAttribute
	private int died;
	@XmlElement
	private Title title;
	@XmlElement
	private Body body;
	@Override
	public String toString() {
		return "Entry [birthplace=" + birthplace + ", id=" + id + ", born=" + born + ", died=" + died + ", title="
				+ title + ", body=" + body + "]";
	}
}

class Title {
	private String csc;
	@XmlValue
	private String content;

	@Override
	public String toString() {
		return "Title [csc=" + csc + ", content=" + content + "]";
	}
}

class Body {
	@XmlElement(name = "p")
	private List<P> p;

	@Override
	public String toString() {
		return "Body [p=" + p + "]";
	}
}

class P {
	@XmlValue
	private String value;

	@Override
	public String toString() {
		return "P [value=" + value + "]";
	}
}
