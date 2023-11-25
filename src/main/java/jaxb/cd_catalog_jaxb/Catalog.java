package jaxb.cd_catalog_jaxb;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog {
	@XmlElement(name = "cd")
	private List<CD> cds;

	@Override
	public String toString() {
		return cds.stream()
				.map((cd) -> cd.toString())
				.reduce((a, b) -> a + "\n" + b)
				.get();
	}

}

@XmlAccessorType(XmlAccessType.FIELD)
class CD {
	private String artist;
	private String title;
	private String country;
	private String company;
	private double price;
	private int year;

	@Override
	public String toString() {
		return "CD [artist=" + artist + ", title=" + title + ", country=" + country + ", company=" + company
				+ ", price=" + price + ", year=" + year + "]";
	}

}
