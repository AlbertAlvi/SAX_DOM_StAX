package dom.questao_09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomBuilderC {

	public static void main(String[] args) {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(new File("./src/main/resources/bibliography.xml"));
			
			double prices = 0;
			int qtdPrices = 0;
			NodeList books = doc.getElementsByTagName("book");
			for(int i = 0; i < books.getLength(); ++i) {
				Element book = (Element) books.item(i);
				String category = book.getAttributes().getNamedItem("category")
						.getNodeValue();
				if(category.equals("SO")) {
					String text = book.getElementsByTagName("price").item(0).getTextContent();
					prices += Double.parseDouble(text);
					qtdPrices++;
				}
			}
			
			System.out.printf("Qual a média de preços dos livros da categoria SO? : %.2f", prices/qtdPrices);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}

}
