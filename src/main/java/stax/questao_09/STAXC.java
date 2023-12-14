package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXC {
    public static void main(String[] args) throws Exception {
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader("./src/main/resources/bibliography.xml"));

        int qtdBooks = 0;
        double sumPrices = 0d;
        double price = 0d;
        boolean bPrice = false;
        boolean bCategory = false;

        while(xsr.hasNext()) {
            switch (xsr.next()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    if(xsr.getLocalName().equals("book")) {
                        bCategory = xsr.getAttributeValue(null, "category").equals("SO") ? true : false;
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
                        if(bCategory) {
                            sumPrices += price;
                            qtdBooks++;
                        }
                        bCategory = false;
                    }
                }
                case XMLStreamConstants.END_DOCUMENT -> {
                    System.out.println(sumPrices / qtdBooks);
                }
            }
        }
        xsr.close();
    }
}
