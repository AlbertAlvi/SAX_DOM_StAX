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

public class DomBuilderB {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("//cd")
					.evaluate(doc, XPathConstants.NODESET);
			
			List<String> list = new ArrayList<>();
			int year = 9999;
			for (int i = 0; i < nodes.getLength(); i++) {
				Element cd = (Element) nodes.item(i);
				int tempYear = Integer.parseInt(cd.getElementsByTagName("year")
						.item(0).getTextContent());
				if(tempYear < year) {
					year = tempYear;
					String title = cd.getElementsByTagName("title").item(0).getTextContent();
					list.add(title);
				} else if(tempYear == year) {
					String title = cd.getElementsByTagName("title").item(0).getTextContent();
					list.add(title);
				}
			}
			
			System.out.println("Qual o nome do álbum mais antigo do catálogo? : (" + year + ") " + list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
