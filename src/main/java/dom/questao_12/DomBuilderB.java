package dom.questao_12;

import java.security.KeyStore.Entry.Attribute;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomBuilderB {
	private static int nRodadas = 0;

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document oldDoc = builder.parse("./src/main/resources/futebol.xml");
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xpath.compile("//rodada/@n")
				.evaluate(oldDoc, XPathConstants.NODESET);

		for (int i = 0; i < nodes.getLength(); i++) {
			int temp = Integer.parseInt(nodes.item(i).getNodeValue());
			if(temp > nRodadas) {
				nRodadas = temp;
			}
		}
		
		Document newDoc = builder.newDocument();
		Element table = newDoc.createElement("table");
		
		Element trH = newDoc.createElement("tr");
		Element th1 = newDoc.createElement("th");
		Element th2 = newDoc.createElement("th");
		
		th1.setTextContent("Rodada");
		th2.setTextContent("Gols");
		
		trH.appendChild(th1);
		trH.appendChild(th2);
		table.appendChild(trH);
		
		for(int i = 0; i < nRodadas; ++i) {
			Element tr = newDoc.createElement("tr");
			Element td1 = newDoc.createElement("td");
			Element td2 = newDoc.createElement("td");
			
			Element rodada = (Element) xpath.compile("//rodada[@n = %d]".formatted(i+1))
				.evaluate(oldDoc, XPathConstants.NODE);
			
			NodeList gols = rodada.getElementsByTagName("gols");
			
			int soma = 0;
			for(int j = 0; j < gols.getLength(); ++j) {
				soma += Integer.parseInt(gols.item(j).getTextContent());
			}
			
			td1.setTextContent(String.valueOf(i+1));
			td2.setTextContent(String.valueOf(soma));
			
			tr.appendChild(td1);
			tr.appendChild(td2);
			table.appendChild(tr);
		}
		
		newDoc.appendChild(table);
		prettyPrint(newDoc);
	}

	private static void prettyPrint(Document newDoc) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		tf.transform(new DOMSource(newDoc), new StreamResult("./xmls/q12B_rodadas.xml"));
	}

}
