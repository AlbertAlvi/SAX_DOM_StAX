package stax.questao_09;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class Template {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/bibliography.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		while (xsr.hasNext()) {
			switch (xsr.next()) {

			}

		}

		fr.close();
		xsr.close();
	}
}
