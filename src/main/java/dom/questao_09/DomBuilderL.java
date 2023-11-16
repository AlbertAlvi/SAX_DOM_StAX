package dom.questao_09;

import java.io.File;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomBuilderL {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("//author")
					.evaluate(doc, XPathConstants.NODESET);
			
			Map<Character, Set<String>> map = new TreeMap<>();
			
			IntStream.range(0, nodeList.getLength())
				.mapToObj(nodeList::item)
				.map(Node::getTextContent)
				.forEach((name) -> {
					Set<String> list = map.getOrDefault(
							name.charAt(0), new TreeSet<>());
					list.add(name);
					map.put(name.charAt(0), list);
				});
			
			map.forEach((k, v) -> System.out.printf("%c: (%d) %s%n", k, v.size(), v));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
