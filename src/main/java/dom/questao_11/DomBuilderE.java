package dom.questao_11;

import java.io.File;
import java.util.Comparator;
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
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomBuilderE {

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document oldDoc = builder.parse("./src/main/resources/chalmers-biography-extract.xml");
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.compile("//entry/@birthplace")
        	.evaluate(oldDoc, XPathConstants.NODESET);

        TreeSet<String> birthPlaces = new TreeSet<>();
        for(int i = 0; i < nodes.getLength(); ++i) {
        	birthPlaces.add(nodes.item(i).getTextContent());
        }
        
        Document newDoc = builder.newDocument();
        Element groups = newDoc.createElement("groups");
        
        birthPlaces.forEach(birthplace -> {
        	try {
        		Element group = newDoc.createElement("group");
        		group.setAttribute("birthplace", birthplace);
        		
				NodeList entryNodes = (NodeList) xpath
						.compile("//entry[@birthplace = '%s']".formatted(birthplace))
						.evaluate(oldDoc, XPathConstants.NODESET);
				
				TreeSet<Element> entries = new TreeSet<>(new EntryComparator());
				
				for (int i = 0; i < entryNodes.getLength(); i++) {
					entries.add((Element) entryNodes.item(i));
				}
				
				for(Element entry : entries) {
					Element person = newDoc.createElement("person");
					
					String namePerson = entry.getElementsByTagName("title").item(0).getTextContent();
					namePerson = namePerson.trim().replaceAll("\\s+", " ");
					
					person.setTextContent(namePerson);
					person.setAttribute("born", entry.getAttribute("born"));
					person.setAttribute("died", entry.getAttribute("died"));
					
					group.appendChild(person);
				}
				
				groups.appendChild(group);
				
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
        });
        
        newDoc.appendChild(groups);
        prettyPrint(newDoc);
	}

	private static void prettyPrint(Document newDoc) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		File file = new File("./xmls/q11E_groups.xml");
		
		tf.transform(new DOMSource(newDoc), new StreamResult(file));
	}
	
	private static class EntryComparator implements Comparator<Element> {
		@Override
		public int compare(Element o1, Element o2) {
			String name1 = o1.getElementsByTagName("title").item(0).getTextContent();
			name1 = name1.trim().replaceAll("\\s+", " ");
			
			String name2 = o2.getElementsByTagName("title").item(0).getTextContent();
			name2 = name2.trim().replaceAll("\\s+", " ");
			
			return name1.compareTo(name2);
		}
	}

}
