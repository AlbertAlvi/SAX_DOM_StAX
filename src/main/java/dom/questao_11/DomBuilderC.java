package dom.questao_11;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

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

import utility_classes.NodeListConverter;

public class DomBuilderC {

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document oldDoc = builder.parse("./src/main/resources/chalmers-biography-extract.xml");
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.compile("//entry[count(./body/p) >= 3]")
        	.evaluate(oldDoc, XPathConstants.NODESET);

        Collection<Element> entrySet = NodeListConverter.toElementCollection(nodes, HashSet::new);
        
        Document newDoc = builder.newDocument();
        Element dictionary = newDoc.createElement("dictionary");
        
        entrySet.forEach(entry -> {
        	Node importNode = newDoc.importNode(entry, true);
        	dictionary.appendChild(importNode);
        });
        
        newDoc.appendChild(dictionary);
        
        prettyPrint(newDoc);
	}

	private static void prettyPrint(Document newDoc) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		File file = new File("./xmls/q11C_entries.xml");
		tf.transform(new DOMSource(newDoc), new StreamResult(file));
	}

}
