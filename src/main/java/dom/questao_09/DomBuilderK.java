package dom.questao_09;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomBuilderK {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("//book/author").evaluate(doc, XPathConstants.NODESET);
			HashSet<String> authors = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item)
					.map(Node::getTextContent).collect(Collectors.toCollection(HashSet::new));

			int qtd = -1;
			Set<String> mostAuthor = new HashSet<>();
			
			for (String author : authors) {
				int count = ((Double) xPath.compile("count(//book[author = '" + author + "'])")
						.evaluate(doc, XPathConstants.NUMBER)).intValue();
				if(count > qtd) {
					mostAuthor = new HashSet<>(List.of(author));
					qtd = count;
				} else if(count == qtd) {
					mostAuthor.add(author);
				}
			}
			
			System.out.println("Qual autor possui mais livros publicados? : " + mostAuthor + 
					"(" + qtd + " publicações)");

//			Map<String, Integer> map = new TreeMap<>();
//			for(int i = 0; i < nodeList.getLength(); ++i) {
//				Element book = (Element) nodeList.item(i);
//				String author = book.getElementsByTagName("author")
//						.item(0).getTextContent();
//				map.put(author, map.getOrDefault(author, 0) + 1);
//			}
//			map.forEach((k, v) -> System.out.printf("-> %s: %d%n", k, v));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
