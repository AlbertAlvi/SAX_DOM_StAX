package dom.questao_09;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomBuilderE {

	public static void main(String[] args) {
		try {
			File file = new File("./src/main/resources/bibliography.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList result = (NodeList) xpath.compile("//book[@category = 'LP']/title[@lang='en']/..")
					.evaluate(doc, XPathConstants.NODESET);
			System.out.println("Quantos livros da categoria LP estão em inglês? : " 
					+ result.getLength());
//			for (int i = 0; i < result.getLength(); i++) {
//				Element e = (Element) result.item(i);
//				System.out.println(e.getAttribute("category"));
//				System.out.println(e.getElementsByTagName("title").item(0)
//						.getAttributes().getNamedItem("lang").getNodeValue());
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
