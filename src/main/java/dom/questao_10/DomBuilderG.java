package dom.questao_10;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomBuilderG {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("//cd")
					.evaluate(doc, XPathConstants.NODESET);
			Map<Integer, Set<String>> map = new TreeMap<>();
			double minPrice = 9999;
			
			for (int i = 0; i < nodes.getLength(); i++) {
				Element cd = (Element) nodes.item(i);
				double tempPrice = Double.parseDouble(cd.getElementsByTagName("price")
						.item(0).getTextContent());
				String title = cd.getElementsByTagName("title").item(0).getTextContent();
				int year = Integer.parseInt(cd.getElementsByTagName("year")
						.item(0).getTextContent());
				
				if(tempPrice < minPrice) {
					minPrice = tempPrice;
					map = new TreeMap<>();
					map.put(year, new TreeSet<>(List.of(title)));
				} else if(tempPrice == minPrice) {
					Set<String> tempSet = map.getOrDefault(year, new TreeSet<>());
					tempSet.add(title);
					map.put(year, tempSet);
				}
			}
			System.out.printf("Em que ano foi lançado o álbum mais barato do catálogo? : price = %.2f%n", minPrice);
			map.forEach((k, v) -> System.out.printf("-> %d: %s%n", k, v));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
