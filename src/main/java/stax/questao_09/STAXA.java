package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class STAXA {
    public static void main(String[] args) throws Exception {
        FileReader fl = new FileReader("./src/main/resources/bibliography.xml");
        XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fl);

        String title = null;
        int qtdAuthor = 0;
        // boolean bAuthor = false;
        boolean bTitle = false;

        while(xsr.hasNext()) {
            switch (xsr.next()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    if(xsr.getLocalName().equals("author")) {
                        qtdAuthor++;
                    } else if(xsr.getLocalName().equals("title")) {
                        bTitle = true;
                    }
                }
                case XMLStreamConstants.CHARACTERS -> {
                    if(bTitle) {
                        bTitle = false;
                        title = xsr.getText();
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    if(xsr.getLocalName().equals("book")) {
                        if(qtdAuthor > 1) {
                            System.out.println(title);
                        }
                        qtdAuthor = 0;
                    }
                }
            }
        }
        xsr.close();
        fl.close();
    }
}
