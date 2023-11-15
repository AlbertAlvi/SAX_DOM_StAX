package dom.questao_09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomBuilderD {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);

			XPath xPath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xPath.compile("//book[year >= 2010 and price > 150]");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList list = (NodeList) result;
			System.out.println("Quantos livros a partir de 2010 possuem preço maior que 150? : " + list.getLength());
			for (int i = 0; i < list.getLength(); ++i) {
				Element e = (Element) list.item(i);
				System.out.println("-> " + e.getElementsByTagName("title")
					.item(0).getTextContent());
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
