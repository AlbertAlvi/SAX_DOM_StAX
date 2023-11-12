package sax;

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
			private int qtdBook = 0;
			private boolean b2010 = false;
			private boolean bYear = false;
			private boolean bPrice = false;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("year")) {
					bYear = true;
				} else if(b2010 && qName.equals("price")) {
					bPrice = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if (bYear) {
					int thisYear = Integer.parseInt(new String(ch, start, length));
					if(thisYear >= 2010) {
						b2010 = true;
					}
					bYear = false;
				} else if(bPrice) {
					double price = Double.parseDouble(new String(ch, start, length));
					if(price > 150) {
						qtdBook++;
					}
					bPrice = false;
					b2010 = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Quantos livros a partir de 2010 possuem preço maior que 150? : " + qtdBook);
			}
		};

		File file = new File("./src/main/resources/bibliography.xml");
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
