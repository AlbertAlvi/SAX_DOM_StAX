package sax.questao_10;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerB {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private List<String> titles = new ArrayList<>();
			private int minYear = 9999;
			
			private boolean bYear;
			private boolean bTitle;
			private int tempYear;
			private String tempTitle;
			
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
					tempTitle = new String(ch, start, length);
					bTitle = false;
				} else if(bYear) {
					tempYear = Integer.parseInt(new String(ch, start, length));
					bYear = false;
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if(qName.endsWith("cd")) {
					if(minYear == tempYear) {
						titles.add(tempTitle);
					} else if(minYear > tempYear) {
						minYear = tempYear;
						titles = new ArrayList<>(List.of(tempTitle));
					}
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Qual o nome do álbum mais antigo do catálogo? : " +
						minYear + " -> " + titles);
			}
		};
		
		File file = new File("./src/main/resources/cd_catalog.xml");

		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(file, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
