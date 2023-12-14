package stax.questao_09;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXH {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/bibliography.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		var bLang = false;
		Set<String> titles = new HashSet<>();

		while (xsr.hasNext()) {
			switch (xsr.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if (xsr.getLocalName().equals("title")) {
					bLang = xsr.getAttributeValue(null, "lang").equals("pt-br");
				}
			}
			case XMLStreamConstants.CHARACTERS -> {
				if(bLang) {
					bLang = false;
					titles.add(xsr.getText());
				}
			}
			case XMLStreamConstants.END_DOCUMENT -> {
				System.out.println(titles);
			}
			}

		}

		fr.close();
		xsr.close();
	}
}
