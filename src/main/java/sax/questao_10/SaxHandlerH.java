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

public class SaxHandlerH {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private List<String> albums = new ArrayList<>();
			private int year = 1987;
			private double maxPrice = 0;
			
			private int tempYear;
			private double tempPrice;
			private String tempTitle;
			private boolean bTitle;
			private boolean bYear;
			private boolean bPrice;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("title")) {
					bTitle = true;
				} else if (qName.equals("year")) {
					bYear = true;
				} else if(qName.equals("price")) {
					bPrice = true;
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if (bTitle) {
					bTitle = false;
					tempTitle = new String(ch, start, length);
				} else if (bYear) {
					bYear = false;
					tempYear = Integer.parseInt(new String(ch, start, length));
				} else if(bPrice) {
					bPrice = false;
					tempPrice = Double.parseDouble(new String(ch, start, length));
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if(qName.equals("cd")) {
//					System.out.println(tempPrice + tempTitle + tempYear);
					if(tempYear == year) {
						if(maxPrice < tempPrice) {
							maxPrice = tempPrice;
							albums = new ArrayList<>(List.of(tempTitle));
						} else if(maxPrice == tempPrice) {
							albums.add(tempTitle);
						}
					}
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.printf("Qual o álbum mais caro lançado no ano de 1987? : %s (%.2f)", albums, maxPrice);
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
