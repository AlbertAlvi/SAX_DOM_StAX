package sax.questao_10;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerD {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private boolean bArtist;
			private int qtdAlbuns;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("artist")) {
					bArtist = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bArtist) {
					if(new String(ch, start, length).charAt(0) == 'B') {
						qtdAlbuns++;						
					}
					bArtist = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.printf("Quantos álbuns possuem artistas com nomes começando pela letra ‘B’? : %d", qtdAlbuns);
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
