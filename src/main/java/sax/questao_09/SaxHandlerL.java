package sax.questao_09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerL {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private Set<String> set = new HashSet<>();
			private boolean bAuthor = false;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("author")) {
					bAuthor = true;
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if (bAuthor) {
					set.add(new String(ch, start, length));
					bAuthor = false;
				}
			}

			@Override
			public void endDocument() throws SAXException {
				Map<Character, List<String>> map = new HashMap<>();
				for (String author : set) {
//					map.put(author.charAt(0), map.getOrDefault(author.charAt(0), "") + author + ", ");
					List<String> tempList = map.getOrDefault(author.charAt(0), new ArrayList<>());
					tempList.add(author);
					map.put(author.charAt(0), tempList);
				}
				map.forEach((k, v) -> System.out.println(k + ": " + v + " " + v.size()));
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
