package dom.questao_10;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomBuilderC {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("//cd")
					.evaluate(doc, XPathConstants.NODESET);
			
			List<String> list = new ArrayList<>();
			double price = 0;
			for (int i = 0; i < nodes.getLength(); i++) {
				Element cd = (Element) nodes.item(i);
				double tempPrice = Double.parseDouble(cd.getElementsByTagName("price")
						.item(0).getTextContent());
				if(tempPrice > price) {
					price = tempPrice;
					String title = cd.getElementsByTagName("title").item(0).getTextContent();
					list.add(title);
				} else if(tempPrice == price) {
					String title = cd.getElementsByTagName("title").item(0).getTextContent();
					list.add(title);
				}
			}
			System.out.printf("Qual o álbum mais caro do catálogo? : (%.2f) %s%n", price, list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
