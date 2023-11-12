package sax;

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
			private int qtdBook = 0;
			private boolean bCategory = false;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("book") && attributes.getValue("category").equals("LP")) {
					bCategory = true;
				} else if (bCategory && qName.equals("title")) {
					if(attributes.getValue("lang").equals("en")) {
						qtdBook++;						
					}
					bCategory = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("Quantos livros da categoria LP estão em inglês? : " + qtdBook);
			}
		};

		File file = new File("./src/main/resources/bibliography.xml");
		SAXParser parser;
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(file, handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
