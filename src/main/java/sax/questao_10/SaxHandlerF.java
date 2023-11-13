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

public class SaxHandlerF {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private List<String> artists = new ArrayList<>();
			private String tempCompany;
			private String tempArtist;
			private boolean bCompany;
			private boolean bArtist;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("artist")) {
					bArtist = true;
				} else if(qName.equals("company")) {
					bCompany = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bArtist) {
					tempArtist = new String(ch, start, length);
					bArtist = false;
				} else if(bCompany) {
					bCompany = false;
					tempCompany = new String(ch, start, length);
				}
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if(qName.equals("cd")) {
					if(tempCompany.equals("Polydor")) {
						artists.add(tempArtist);
					}
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.printf("Quais artistas lançaram álbuns pela Polydor? : %s (%d)", artists, artists.size());
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
