package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXI {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/bibliography.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		double price = 0;
		double enPrices = 0;
		double ptbrPrices = 0;
		int qtdEN = 0;
		int qtdPTBR = 0;
		boolean flagEN = false;
		boolean flagPTBR = false;
		boolean bPrice = false;

		while (xsr.hasNext()) {
			switch (xsr.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if(xsr.getLocalName().equals("title")) {
					var lang = xsr.getAttributeValue(null, "lang");
					flagEN = lang.equals("en");
					flagPTBR = lang.equals("pt-br");
				} else if(xsr.getLocalName().equals("price")) {
					bPrice = true;
				}
			}
			case XMLStreamConstants.CHARACTERS -> {
				if(bPrice) {
					bPrice = false;
					price = Double.parseDouble(xsr.getText());
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if(xsr.getLocalName().equals("book")) {
					if(flagPTBR) {
						flagPTBR = false;
						qtdPTBR++;
						ptbrPrices += price;
					} else if(flagEN) {
						flagEN = false;
						qtdEN++;
						enPrices += price;
					}
				}
			}
			case XMLStreamConstants.END_DOCUMENT -> {
				System.out.println(ptbrPrices / qtdPTBR + " > " + enPrices / qtdEN);
				System.out.println(ptbrPrices / qtdPTBR > enPrices / qtdEN);
			}
			}

		}

		fr.close();
		xsr.close();
	}
}
