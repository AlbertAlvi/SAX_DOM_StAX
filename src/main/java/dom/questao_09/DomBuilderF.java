package dom.questao_09;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomBuilderF {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nl = (NodeList) xPath.compile("//author[starts-with(., 'A')]")
					.evaluate(doc, XPathConstants.NODESET);
			
			Set<String> collect = IntStream.range(0, nl.getLength())
					.mapToObj(nl::item)
					.map(Node::getTextContent)
					.collect(Collectors.toCollection(HashSet::new));

			System.out.printf("Quantos autores começam com a letra ‘A’? : %d%n", collect.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
