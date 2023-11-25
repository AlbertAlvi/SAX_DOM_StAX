package jaxb.bibliography_jaxb;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class BibliographyJAXB {

	public static void main(String[] args) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(Bibliography.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Bibliography b = (Bibliography) um.unmarshal(
				new File("./src/main/resources/bibliography.xml"));
		System.out.println(b);
	}

}
