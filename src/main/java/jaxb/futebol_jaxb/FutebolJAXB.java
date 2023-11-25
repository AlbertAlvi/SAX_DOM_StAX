package jaxb.futebol_jaxb;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class FutebolJAXB {
	public static void main(String[] args) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(Clube.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Clube clube = (Clube) um.unmarshal(new File("./src/main/resources/futebol.xml"));
		System.out.println(clube);
	}
}
