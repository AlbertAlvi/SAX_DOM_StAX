package sax.questao_09;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerF_G {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private boolean bAuthor = false;
			Set<String> set = new HashSet<>();
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("author")) {
					bAuthor = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bAuthor) {
					String name = new String(ch, start, length);
					if(name.charAt(0) == 'A' || name.charAt(0) == 'a') {
						set.add(name);
					}
					bAuthor = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Quantos autores começam com a letra ‘A’? : " + set.size());
				System.out.println("Quais autores começam com a letra ‘A’? : " + 
				set.stream().collect(Collectors.joining(", ")));
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
