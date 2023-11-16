package dom.questao_10;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DomBuilderE {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			Double qtd = (Double) xPath.compile("count(//cd[year < 1990 and year >= 1980])")
				.evaluate(doc, XPathConstants.NUMBER);
			
			System.out.printf("Quantos álbuns foram lançados na década de 1980? : %.0f", qtd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
