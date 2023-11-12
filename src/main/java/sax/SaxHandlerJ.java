package sax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerJ {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private boolean bYear = false;
			private boolean bAuthor = false;
			private boolean bNameAuthor = false;
			private int qtdBooks = 0;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) {
				if(qName.equals("author")) {
					bAuthor = true;
				} else if(qName.equals("year")) {
					bYear = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bAuthor) {
					if(new String(ch, start, length).equals("Abraham Silberschatz")) {
//						System.out.println("Abraham Silberschatz");
						bNameAuthor = true;
					}
					bAuthor = false;
				}
				else if(bYear) {
					if(bNameAuthor) {
						int year = Integer.parseInt(new String(ch, start, length));
						if(year == 2012) {
							qtdBooks++;
							bNameAuthor = false;
						}
					}
					bYear = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Quantos livros ‘Abraham Silberschatz’ publicou em 2012? : " +
						qtdBooks);
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
