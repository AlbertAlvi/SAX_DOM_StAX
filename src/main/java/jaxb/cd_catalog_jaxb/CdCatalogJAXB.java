package jaxb.cd_catalog_jaxb;

import java.io.File;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class CdCatalogJAXB {

	public static void main(String[] args) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(Catalog.class);
		Unmarshaller um = ctx.createUnmarshaller();
		Catalog c = (Catalog) um.unmarshal(new File("./src/main/resources/cd_catalog.xml"));
		System.out.println(c);
	}

}
