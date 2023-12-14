package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXE {

	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/bibliography.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		boolean bLang = false;
		boolean bCategory = false;
		int count = 0;
		
		while (xsr.hasNext()) {
			switch (xsr.next()) {
				case XMLStreamConstants.START_ELEMENT -> {
					if(xsr.getLocalName().equals("book")) {
						bCategory =	xsr.getAttributeValue(null, "category").equals("LP");
					} else if(xsr.getLocalName().equals("title")) {
						bLang = xsr.getAttributeValue(null, "lang").equals("en") ? true : false;
						count += bLang && bCategory ? 1 : 0;
						
						bLang = false;
						bCategory = false;
					}
				}
				case XMLStreamConstants.END_DOCUMENT -> {
					System.out.println(count);
				}
			}
		}

		fr.close();
		xsr.close();
	}

}
