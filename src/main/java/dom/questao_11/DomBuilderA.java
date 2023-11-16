package dom.questao_11;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.util.Comparator;
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

import dom.questao_09.NodeListConverter;

public class DomBuilderA {
	public static void main(String[] args) throws Exception {
        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document oldDoc = builder.parse("./src/main/resources/chalmers-biography-extract.xml");
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.compile("//entry")
        	.evaluate(oldDoc, XPathConstants.NODESET);
        
        Set<Node> entrySet = (Set<Node>) NodeListConverter.toCollection(nodes, HashSet::new);
        Set<String> nameSet = new TreeSet<>(new DiedComparator());
        
        entrySet.stream()
        	.forEach(entry -> {
        		Element e = (Element) entry;
        		String name = e.getElementsByTagName("title")
        			.item(0).getTextContent();
        		name = name.trim().replaceAll("\\s+|\\(|\\)", " ");
        		Double died = Double.parseDouble(e.getAttribute("died"));
        		
        		nameSet.add(name + " (%.0f)".formatted(died));
        	});
         
//      nameSet.forEach(name -> System.out.printf("%s%n", name));
        
        Document newDoc = builder.newDocument();
        Element root = newDoc.createElement("root");
        
        nameSet.forEach(name -> {
        	Element dude = newDoc.createElement("dude");
        	dude.setTextContent(name);
        	root.appendChild(dude);
        });
        
        newDoc.appendChild(root);
        prettyPrint(newDoc);
    }
     
    public static final void prettyPrint(Document xml) throws Exception {
    	Transformer transformer = TransformerFactory.newInstance().newTransformer();
    	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
    	DOMSource source = new DOMSource(xml);
    	
    	StreamResult file = new StreamResult(new File("./xmls/q11A_dudes.xml"));
    	transformer.transform(source, file);
    }
    
    static class DiedComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			int idx1 = o1.indexOf('(');
			int idx2 = o1.indexOf(')');
			int year1 = Integer.parseInt(o1.substring(idx1+1, idx2));
			
			idx1 = o2.indexOf('(');
			idx2 = o2.indexOf(')');
			int year2 = Integer.parseInt(o2.substring(idx1+1, idx2));
			
//			System.out.println(year1 + " " + year2);
			
			return year1 - year2;
		}
    	
    }
}
