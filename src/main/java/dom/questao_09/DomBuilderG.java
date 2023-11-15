package dom.questao_09;

import java.io.File;
import java.util.HashSet;
import java.util.List;
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

public class DomBuilderG {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nl = (NodeList) xPath.compile("//author[starts-with(., 'A')]")
					.evaluate(doc, XPathConstants.NODESET);

			System.out.printf("Quais autores começam com a letra ‘A’? :%n");
			Set<String> nodes = IntStream.range(0, nl.getLength())
				    .mapToObj(nl::item)
				    .map(Node::getTextContent)
				    .collect(Collectors.toCollection(HashSet::new));
			System.out.println(nodes);
			
			/*observe que na última etapa, temos uma stream de <String>, mas ainda
			sim o compilador não detecta erros e o código roda com o Set sendo 
			do tipo <Node>. Isso ocorre por causa do Type Safety, que faz uma unchecked
			conversion de String para Node */
//			Set<Node> nodes = IntStream.range(0, nl.getLength())
//				    .mapToObj(nl::item)
//				    .map(Node::getTextContent)
//				    .collect(Collectors.toCollection(() -> new HashSet())); //o problema reside nessa lambda
//			System.out.println(nodes);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
