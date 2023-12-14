package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXD {
    public static void main(String[] args) throws Exception {
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader("./src/main/resources/bibliography.xml"));

        int qtdBooks = 0;
        int year = 0;
        double price = 0;
        boolean bYear = false;
        boolean bPrice = false;

        while(xsr.hasNext()) {
            switch (xsr.next()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    if(xsr.getLocalName().equals("year")) {
                        bYear = true;
                    } else if(xsr.getLocalName().equals("price")) {
                        bPrice = true;
                    } 
                }
                case XMLStreamConstants.CHARACTERS -> {
                    if(bYear) {
                        bYear = false;
                        year = Integer.parseInt(xsr.getText());
                    } else if(bPrice) {
                        bPrice = false;
                        price = Double.parseDouble(xsr.getText());
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    if(xsr.getLocalName().equals("book")) {
                        if(year >= 2010 && price >= 150.0) {
                            qtdBooks++;
                            System.out.println(year + " " + price);
                        }
                    }
                }
                case XMLStreamConstants.END_DOCUMENT -> {
                    System.out.println(qtdBooks);
                }
            }
        }
        xsr.close();
    }
}
