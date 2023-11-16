package dom.questao_10;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DomBuilderK {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodesCompany = (NodeList) xPath.compile("//company")
				.evaluate(doc, XPathConstants.NODESET);
			
			Set<String> setCompany = new HashSet<>();
			for (int i = 0; i < nodesCompany.getLength(); i++) {
				setCompany.add(nodesCompany.item(i).getTextContent());
			}
			
			Map<String, Integer> mapCompany = new TreeMap<>();
			for(String company : setCompany) {
				int qtd = ((Double) xPath.compile("count(//cd[company = '%s'])".formatted(company))
				.evaluate(doc, XPathConstants.NUMBER)).intValue();
				mapCompany.put(company, qtd);
			}
			
			System.out.printf("Quantos álbuns foram lançados por cada gravadora? :%n");
			mapCompany.forEach((k, v) -> System.out.printf("%-14s: %d%n", k, v));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}