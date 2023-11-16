package dom.questao_09;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class DomBuilderJ {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			Double count = (Double) xPath.compile("count(//book[author = 'Abraham Silberschatz' and year = 2012])")
				.evaluate(doc, XPathConstants.NUMBER);
			System.out.println("Quantos livros ‘Abraham Silberschatz’ publicou em 2012? : "
					+ count.intValue());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
