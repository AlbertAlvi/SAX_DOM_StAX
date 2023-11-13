package sax.questao_09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// import ldd.BibliographyFlag;

public class SaxHandlerB extends DefaultHandler {
//    private BibliographyFlag flag = null;
    private int qtdAuthor = 0;
    private int qtdBook = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "author":
                qtdAuthor++;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "book":
                if (qtdAuthor > 1)
                    qtdBook++;
                qtdAuthor = 0;
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Quantidade de livros com mais de dois autores: " + qtdBook);
    }

    public static void main(String[] args) {
        File file = new File("./src/main/resources/bibliography.xml");
        SaxHandlerB handler = new SaxHandlerB();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
