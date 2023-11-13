package sax.questao_10;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerC {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private List<String> titles = new ArrayList<>();
			private double maxPrice = 0.00;
			
			private boolean bPrice;
			private boolean bTitle;
			private String tempTitle;
			private double tempPrice;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("title")) {
					bTitle = true;
				} else if(qName.equals("price")) {
					bPrice = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bTitle) {
					tempTitle = new String(ch, start, length);
					bTitle = false;
				} else if(bPrice) {
					tempPrice = Double.parseDouble(new String(ch, start, length));
					bPrice = false;
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if (qName.equals("cd")) {
					if(tempPrice == maxPrice) {
						titles.add(tempTitle);
					} else if(tempPrice > maxPrice) {
						maxPrice = tempPrice;
						titles = new ArrayList<>(List.of(tempTitle));
					}
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Qual o álbum mais caro do catálogo? : "
						+ maxPrice + " -> " + titles + "(" + titles.size() + ")");
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
