package stax.questao_09;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXF {

	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/bibliography.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		char letra = 'A';
		boolean bAuthor = false;
		Set<String> authors = new HashSet<>();

		while (xsr.hasNext()) {
			switch (xsr.next()) {
			case XMLStreamConstants.START_ELEMENT:
				if (xsr.getLocalName().equals("author")) {
					bAuthor = true;
				}
				break;

			case XMLStreamConstants.CHARACTERS:
				if (bAuthor) {
					bAuthor = false;
					String text = xsr.getText();
					if(text.charAt(0) == letra) {
						authors.add(text);						
					}
				}
				break;
				
			case XMLStreamConstants.END_DOCUMENT:
				System.out.println(authors.size());
				break;
			}
		}

		fr.close();
		xsr.close();
	}

}
