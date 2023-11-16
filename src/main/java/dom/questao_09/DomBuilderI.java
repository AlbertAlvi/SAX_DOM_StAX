package dom.questao_09;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class DomBuilderI {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			Double ptSum = (Double) xPath.compile("sum(//book/title[@lang='pt-br']/../price)")
					.evaluate(doc, XPathConstants.NUMBER);
			Double ptCount = (Double) xPath.compile("count(//book/title[@lang='pt-br'])")
					.evaluate(doc, XPathConstants.NUMBER);
			
			Double enSum = (Double) xPath.compile("sum(//book/title[@lang='en']/../price)")
					.evaluate(doc, XPathConstants.NUMBER);
			Double enCount = (Double) xPath.compile("count(//book/title[@lang='en'])")
					.evaluate(doc, XPathConstants.NUMBER);
			
			System.out.println("A média de preço dos livros em português é maior que dos livros em inglês? : "
					+ ((ptSum/ptCount) > (enSum/enCount) ? "sim" : "não"));
			System.out.println("pt: " + (ptSum/ptCount));
			System.out.println("en: " + (enSum/enCount));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
