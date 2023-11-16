package dom.questao_10;

import java.io.File;
import java.util.stream.IntStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomBuilderF {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/cd_catalog.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.compile("//cd[company = 'Polydor']/artist")
				.evaluate(doc, XPathConstants.NODESET);
			
			System.out.println("Quais artistas lançaram álbuns pela Polydor?");
			IntStream.range(0, nodes.getLength())
				.mapToObj(nodes::item)
				.map(Node::getTextContent)
				.forEach((artist) -> System.out.printf("-> %s%n", artist));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}