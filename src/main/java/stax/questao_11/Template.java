package stax.questao_11;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;

public class Template {

	public static void main(String[] args) throws Exception {
		FileReader fileR = new FileReader("./src/main/resources/chalmers-biography-extract.xml");
		var reader = XMLInputFactory.newInstance().createXMLStreamReader(fileR);

		while(reader.hasNext()) {
			switch(reader.next()) {
			
			}
		}
		
		fileR.close();
		reader.close();
	}

}
