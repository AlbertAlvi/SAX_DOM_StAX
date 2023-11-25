package jaxb.bibliography_jaxb;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bibliography {
	@XmlElement(name = "book")
	private List<Book> books;
	
	@Override
	public String toString() {
		return books.stream()
				.map((book) -> book.toString())
				.reduce((a, b) -> a + "\n" + b)
				.get();
	}
}
