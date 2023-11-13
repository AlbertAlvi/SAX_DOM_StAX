package sax.questao_10;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerE {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private boolean bYear;
			private int decade = 198; //1980
			private int qtdAlbuns;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("year")) {
					bYear = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bYear) {
					int year = Integer.parseInt(new String(ch, start, length));
					if(year/10 == decade) {
						qtdAlbuns++;
					}
					bYear = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.printf("Quantos álbuns foram lançados na década de 1980? : %d", qtdAlbuns);
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
