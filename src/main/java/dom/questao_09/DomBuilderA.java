package dom.questao_09;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomBuilderA {

	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("./src/main/resources/bibliography.xml"));
			
			List<String> titles = new ArrayList<>();
			NodeList nList = doc.getElementsByTagName("book");
			for(int i = 0; i < nList.getLength(); ++i) {
				Element book = (Element) nList.item(i);
				NodeList authors = book.getElementsByTagName("author");
				if(authors.getLength() > 1) {
					titles.add(book.getElementsByTagName("title").item(0).getTextContent());
				}
			}
			
			System.out.println("Qual o nome dos livros que possuem mais de um autor? :");
			titles.forEach((t) -> System.out.printf("%s%n", t));
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

}
