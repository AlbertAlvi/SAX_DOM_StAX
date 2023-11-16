package dom.questao_09;

import java.io.File;
import java.util.Collection;
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

public class DomBuilderH {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nl = (NodeList) xPath.compile("//book/title[@lang = 'pt-br']")
					.evaluate(doc, XPathConstants.NODESET);
			
			Set<Node> collection = (Set<Node>) NodeListConverter.toCollection(nl, HashSet::new);
			Set<String> bookSet = collection.stream()
					.map(Node::getTextContent)
					.collect(Collectors.toCollection(HashSet::new));
			
			System.out.println("Quais os nomes dos livros em português? :");
			bookSet.forEach((book) -> System.out.printf("-> %s%n", book));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
