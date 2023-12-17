package stax.questao_12;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;

public class Template {
	public static void main(String[] args) throws Exception {
		FileReader fileR = new FileReader("./src/main/resources/futebol.xml");
		var reader = XMLInputFactory.newInstance().createXMLStreamReader(fileR);

		while(reader.hasNext()) {
			switch(reader.next()) {
			
			}
		}
		
		fileR.close();
		reader.close();
	}
}
