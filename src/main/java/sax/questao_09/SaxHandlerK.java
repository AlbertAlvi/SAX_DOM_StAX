package sax.questao_09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerK {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private boolean bAuthor = false;
			private Map<String, Integer> map = new HashMap<>();
			
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
					map.put(name, map.getOrDefault(name, 0) + 1);
					bAuthor = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				int max = map.values().stream().max(Integer::compareTo).get();
				map.keySet().stream().forEach((author) -> {
					if(map.get(author) == max) {
						System.out.printf("%s, ", author);
					}
				});
				System.out.println("(max: " + max + ")");
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
