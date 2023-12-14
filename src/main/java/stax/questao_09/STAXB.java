package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXB  {
    public static void main(String[] args) throws Exception {
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader("./src/main/resources/bibliography.xml"));

        int qtdAuthor = 0;
        int qtdBooks = 0;

        while(xsr.hasNext()) {
            switch (xsr.next()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    if(xsr.getLocalName().equals("author")) {
                        qtdAuthor++;
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    if(xsr.getLocalName().equals("book")) {
                        if(qtdAuthor > 1) {
                            qtdBooks++;
                        }
                        qtdAuthor = 0;
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
