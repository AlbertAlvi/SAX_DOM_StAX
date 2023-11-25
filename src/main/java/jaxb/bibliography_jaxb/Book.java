package jaxb.bibliography_jaxb;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
	@XmlAttribute
	private String category;
	private Title title;
	@XmlElement(name = "author")
	private List<String> authors;
	private int year;
	private double price;

	@Override
	public String toString() {
		return "Book [category=" + category + ", title=" + title + ", authors=" + authors + ", year=" + year
				+ ", price=" + price + "]";
	}
}

class Title {
	@XmlValue
	private String value;
	@XmlAttribute
	private String lang;

	@Override
	public String toString() {
		return "Title [value=" + value + ", lang=" + lang + "]";
	}
}
