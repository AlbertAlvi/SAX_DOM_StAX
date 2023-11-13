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

public class SaxHandlerG {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private List<Integer> years = new ArrayList<>();
			private double minPrice = 9999;
			private int tempYear;
			private double tempPrice;
			private boolean bPrice;
			private boolean bYear;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("year")) {
					bYear = true;
				} else if(qName.equals("price")) {
					bPrice = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bYear) {
					tempYear = Integer.parseInt(new String(ch, start, length));
					bYear = false;
				} else if(bPrice) {
					tempPrice = Double.parseDouble(new String(ch, start, length));
					bPrice = false;
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if(qName.equals("cd")) {
					if(minPrice > tempPrice) {
						minPrice = tempPrice;
						years = new ArrayList<>(List.of(tempYear));
					} else if(minPrice == tempPrice) {
						years.add(tempYear);
					}
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.printf("Em que ano foi lançado o álbum mais barato do catálogo? : %s (%.2f)", years, minPrice);
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
