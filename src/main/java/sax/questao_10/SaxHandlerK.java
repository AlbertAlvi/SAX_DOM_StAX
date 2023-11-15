package sax.questao_10;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
			private Map<String, Integer> map = new HashMap<>();
			private boolean bCompany;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("company")) {
					bCompany = true;
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if (bCompany) {
					bCompany = false;
					String tmp = new String(ch, start, length);
					map.put(tmp, map.getOrDefault(tmp, 0) + 1);
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Quantos álbuns foram lançados por cada gravadora? : ");
				map.forEach((k, v) -> System.out.printf("%-14s: %d%n", k, v));
//				System.out.printf("%2.2s", "Hi there!");
			}
		};

		File file = new File("./src/main/resources/cd_catalog.xml");

		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(file, handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}
