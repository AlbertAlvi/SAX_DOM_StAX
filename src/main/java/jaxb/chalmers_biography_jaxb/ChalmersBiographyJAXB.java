package jaxb.chalmers_biography_jaxb;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ChalmersBiographyJAXB {

	public static void main(String[] args) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(Dictionary.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Dictionary d = (Dictionary) um.unmarshal(new File("./src/main/resources/chalmers-biography-extract.xml"));
		System.out.println(d);
	}

}
