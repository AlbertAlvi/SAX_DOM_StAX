package sax;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerH {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private boolean bTitle = false;
			private Set<String> set = new HashSet<>();
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("title") && attributes.getValue("lang").equals("pt-br")) {
					bTitle = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bTitle) {
					set.add(new String(ch, start, length));
					bTitle = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Quais os nomes dos livros em português? : "
						+ set);
			}
		};
		
		File file = new File("./src/main/resources/bibliography.xml");
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(file, handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}
