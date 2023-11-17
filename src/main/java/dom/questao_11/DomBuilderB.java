package dom.questao_11;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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

import dom.questao_09.NodeListConverter;

public class DomBuilderB {

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document oldDoc = builder.parse("./src/main/resources/chalmers-biography-extract.xml");
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.compile("//entry[contains(., 'Oxford')]")
        	.evaluate(oldDoc, XPathConstants.NODESET);

        Collection<Node> entrySet = NodeListConverter.toCollection(nodes, HashSet::new);

        TreeSet<String> nameSet = entrySet.stream()
        	.map((entry) -> {
        		Element e = (Element) entry;
        		String name = e.getElementsByTagName("title").item(0).getTextContent();
        		return name.trim().replace("\\s+", " ");
        	})
        	.collect(Collectors.toCollection(TreeSet::new));
        
//        nameSet.forEach(System.out::println);
        
        Document newDoc = builder.newDocument();
        Element ul = newDoc.createElement("ul");
        
        nameSet.forEach((name) -> {
        	Element li = newDoc.createElement("li");
        	li.setTextContent(name);
        	ul.appendChild(li);
        });
        
        newDoc.appendChild(ul);
        prettyPrint(newDoc);
	}
	
	public static void prettyPrint(Document doc) throws Exception {
		Transformer tf = TransformerFactory.newDefaultInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
//		tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
		
		File file = new File("./xmls/q11B_ul.xml");
		StreamResult sr = new StreamResult(file);
		
		tf.transform(new DOMSource(doc), sr);
	}

}
