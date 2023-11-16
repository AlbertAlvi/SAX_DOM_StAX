package dom.questao_10;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DomBuilderJ {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("//artist")
				.evaluate(doc, XPathConstants.NODESET);
			
			Set<String> set = new TreeSet<>();
			for (int i = 0; i < nodes.getLength(); i++) {
				set.add(nodes.item(i).getTextContent());
			}
			
			System.out.println("Quantos artistas distintos existem no catálogo? : " + set.size());
			set.forEach(a -> System.out.printf("-> %s%n", a));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
