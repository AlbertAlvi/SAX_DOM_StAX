package dom.questao_10;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class DomBuilderD {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			Double qtd = (Double) xPath.compile("count(//cd/artist[starts-with(., 'B')])")
				.evaluate(doc, XPathConstants.NUMBER);
			System.out.printf("Quantos álbuns possuem artistas com "
					+ "nomes começando pela letra ‘B’? : %.0f", qtd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
