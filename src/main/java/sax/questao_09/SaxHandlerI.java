package sax.questao_09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerI {

	public static void main(String[] args) {
		DefaultHandler handler = new DefaultHandler() {
			private double enPrices = 0;
			private double ptbrPrices = 0;
			private int qtdEN = 0;
			private int qtdPTBR = 0;
			private boolean flagEN = false;
			private boolean flagPTBR = false;
			private boolean bPrice = false;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {
				if(qName.equals("title")) {
					String lang = attributes.getValue("lang");
					if(lang.equals("en")) {
						flagEN = true;					
					} else if (lang.equals("pt-br")) {
						flagPTBR = true;
					}
				}
				
				if(qName.equals("price")) {
					bPrice = true;
				}
			}
			
			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if(bPrice) {
					if(flagEN) {
						enPrices += Double.parseDouble(new String(ch, start, length));
						qtdEN++;
						flagEN = false;
					} else if(flagPTBR) {
						ptbrPrices += Double.parseDouble(new String(ch, start, length));
						qtdPTBR++;
						flagPTBR = false;
					}
					bPrice = false;
				}
			}
			
			@Override
			public void endDocument() throws SAXException {
				System.out.println("A média de preço dos livros em português é maior que dos livros em inglês? : " +
						((ptbrPrices / qtdPTBR) > (enPrices / qtdEN) ? "sim" : "não"));
				System.out.println((ptbrPrices / qtdPTBR));
				System.out.println((enPrices / qtdEN));
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
