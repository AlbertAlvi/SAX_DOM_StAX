package dom.questao_09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomBuilderB {

	public static void main(String[] args) {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(new File("./src/main/resources/bibliography.xml"));
			
			int qtdBooks = 0;
			NodeList books = doc.getElementsByTagName("book");
			for(int i = 0; i < books.getLength(); ++i) {
				Element book = (Element) books.item(i);
				NodeList authors = book.getElementsByTagName("author");
				if(authors.getLength() > 1) {
					qtdBooks++;
				}
			}
			
			System.out.println("Quantos livros possuem mais de um autor? : " + qtdBooks);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

}
