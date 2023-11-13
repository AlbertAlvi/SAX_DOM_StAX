package sax.questao_09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerA extends DefaultHandler {
    private BibliographyFlag flag = null;
    private int qtdAuthor = 0;
    private String tempTitle = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "title":
                flag = BibliographyFlag.TITLE;
                break;
            case "author":
                flag = BibliographyFlag.AUTHOR;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (flag != null) {
            switch (flag) {
                case TITLE:
                    tempTitle = new String(ch, start, length);
                    break;
                case AUTHOR:
                    qtdAuthor++;
                    break;
            }
            flag = null;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "book":
                if (qtdAuthor > 1)
                    System.out.println(tempTitle);
                qtdAuthor = 0;
                break;
        }
    }

    public static void main(String[] args) {

        File file = new File("./src/main/resources/bibliography.xml");
        SaxHandlerA handler = new SaxHandlerA();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(file, handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
