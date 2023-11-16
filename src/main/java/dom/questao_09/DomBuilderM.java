package dom.questao_09;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomBuilderM {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("//book")
					.evaluate(doc, XPathConstants.NODESET);

			Map<Integer, Set<String>> map = new TreeMap<>();
			for(int i = 0; i < nodeList.getLength(); ++i) {
				Element book = (Element) nodeList.item(i); 
				String title = book.getElementsByTagName("title")
						.item(0).getTextContent();
				int decade = Integer.parseInt(
						book.getElementsByTagName("year")
						.item(0).getTextContent()) / 10 * 10;
				
				Set<String> set = map.getOrDefault(decade, new TreeSet<>());
				set.add(title);
				map.put(decade, set);
			}
			
			map.forEach((k, v) -> System.out.printf("%d: %s%n", k, v));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
