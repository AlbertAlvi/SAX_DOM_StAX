package sax.questao_09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerM {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private Map<Integer, List<String>> map = new HashMap<>();
			private String title = null;
			private int year = 0;
			private boolean bTitle = false;
			private boolean bYear = false;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("title")) {
					bTitle = true;
				} else if(qName.equals("year")) {
					bYear = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bTitle) {
					title = new String(ch, start, length);
					bTitle = false;
				} else if(bYear) {
					year = Integer.parseInt(new String(ch, start, length)) / 10 * 10;
					bYear = false;
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if(qName.equals("book")) {
					List<String> temp = map.getOrDefault(year, new ArrayList<>());
					temp.add(title);
					map.put(year, temp);
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				map.forEach((k, v) -> System.out.printf("%d: %s (%d)\n", k, v, v.size()));
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
