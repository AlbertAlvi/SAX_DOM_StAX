package dom.questao_12;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utility_classes.NodeListConverter;

public class DomBuilderA {
	private static Integer maxGols = 0;
	private static List<Element> maxJogo = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document oldDoc = builder.parse("./src/main/resources/futebol.xml");
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xpath.compile("//jogo")
				.evaluate(oldDoc, XPathConstants.NODESET);
		
		var jogoSet = NodeListConverter.toElementCollection(nodes, HashSet::new);
		
		jogoSet.forEach((jogo) -> {
			NodeList nodeGols = jogo.getElementsByTagName("gols");
			var golsSet = NodeListConverter.toElementCollection(nodeGols, HashSet::new);
			int totalGols = golsSet.stream()
					.map(element -> Integer.parseInt(element.getTextContent()))
					.reduce(0, (a, b) -> a + b);
			
			if(totalGols > maxGols) {
				maxGols = totalGols;
				maxJogo = new ArrayList<>(List.of(jogo));
			} else if(totalGols == maxGols) {
				maxJogo.add(jogo);
			}
		});
		
		Document newDoc = builder.newDocument();
		Element jogos = newDoc.createElement("jogos");
		jogos.setAttribute("max-gols", maxGols.toString());
		
		maxJogo.forEach((jogo) -> {
			Node newJogo = newDoc.importNode(jogo, true);
			jogos.appendChild(newJogo);
		});
		
		newDoc.appendChild(jogos);
		prettyPrint(newDoc);
	}

	private static void prettyPrint(Document newDoc) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.METHOD, "xml");
		tf.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
		
		tf.transform(new DOMSource(newDoc), 
				new StreamResult(new FileWriter("./xmls/q12A_jogos.xml")));
	}

}
