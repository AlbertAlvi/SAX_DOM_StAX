package dom.questao_11;

import java.io.File;
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

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import utility_classes.NodeListConverter;

public class DomBuilderD {

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document oldDoc = builder.parse("./src/main/resources/chalmers-biography-extract.xml");
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.compile("//entry[@died - @born > 88]/title")
        	.evaluate(oldDoc, XPathConstants.NODESET);
        
        Collection<Element> titleSet = NodeListConverter.toElementCollection(nodes, HashSet::new);
        TreeSet<String> names = new TreeSet<>();
        
        titleSet.forEach((title) -> {
        	String str = title.getTextContent().trim().replaceAll("\\s+", " ");
        	names.add(str);
        });
        
        Document newDoc = builder.newDocument();
        Element people = newDoc.createElement("people");
        Comment comment = newDoc.createComment("People who died after more than 88 years");
        people.appendChild(comment);
        
        names.forEach(str -> {
        	Element name = newDoc.createElement("name");
        	name.setTextContent(str);
        	people.appendChild(name);
        });
        
        newDoc.appendChild(people);
        prettyPrint(newDoc);
	}

	private static void prettyPrint(Document newDoc) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		File file = new File("./xmls/q11D_people.xml");
		
		tf.transform(new DOMSource(newDoc), new StreamResult(file));
	}

}
