package sax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerC extends DefaultHandler {
	private boolean bBook = false;
	private boolean bPrice = false;
	private double prices = 0;
	private int qtdPrices = 0;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("book") && attributes.getValue("category").equals("SO")) {
			bBook = true;
			qtdPrices++;
		} else if (bBook && qName.equals("price")) {
			bPrice = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (bPrice) {
			prices += Double.parseDouble(new String(ch, start, length));
			bPrice = false;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("book")) {
			bBook = false;
//			System.out.println(prices);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("A média de preços dos livros de SO é: " + (prices / qtdPrices));
	}

	public static void main(String[] args) {
		File file = new File("./src/main/resources/bibliography.xml");
		SaxHandlerC handler = new SaxHandlerC();

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
