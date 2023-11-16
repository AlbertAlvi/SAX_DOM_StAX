package dom.questao_10;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomBuilderH {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("//cd[year = 1987]")
					.evaluate(doc, XPathConstants.NODESET);
			
			Set<String> set = new TreeSet<>();
			double maxPrice = 0;
			for (int i = 0; i < nodes.getLength(); i++) {
				Element cd = (Element) nodes.item(i);
				Double price = Double.parseDouble(cd.getElementsByTagName("price")
						.item(0).getTextContent());
				String title = cd.getElementsByTagName("title")
						.item(0).getTextContent();
				if(price > maxPrice) {
					maxPrice = price;
					set = new TreeSet<>(List.of(title));
				} else if(price == maxPrice) {
					set.add(title);
				}
			}
			System.out.printf("Qual o álbum mais caro lançado no ano de 1987? : price = %.2f%n", maxPrice);
			set.forEach(t -> System.out.printf("-> %s%n",t));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
