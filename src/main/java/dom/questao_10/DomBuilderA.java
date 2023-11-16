package dom.questao_10;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class DomBuilderA {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			Double sum = (Double) xPath.compile("sum(//cd/price)")
					.evaluate(doc, XPathConstants.NUMBER);
			int count = ((Double) xPath.compile("count(//cd/price)")
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			
			System.out.println("Qual a média de preços dos álbuns do catálogo? : " + (sum/count));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
