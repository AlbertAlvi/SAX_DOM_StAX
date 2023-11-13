package sax.questao_10;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerA {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private double prices;
			private boolean bPrice;
			private int qtdPrices;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if (qName.equals("price")) {
					bPrice = true;
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if (bPrice) {
					prices += Double.parseDouble(new String(ch, start, length));
					qtdPrices++;
					bPrice = false;
				}
			}

			@Override
			public void endDocument() throws SAXException {
				System.out.println("Qual a média de preços dos álbuns do catálogo? : " 
						+ (prices / qtdPrices));
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
