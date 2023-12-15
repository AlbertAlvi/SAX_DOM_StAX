package stax.questao_11;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXB {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/chalmers-biography-extract.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		Set<String> set = new TreeSet<>();

		var title = "";
		var bWord = false;

		while (xsr.hasNext()) {
			switch (xsr.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if ("body".equals(xsr.getLocalName())) {
					bWord = checkWord("Oxford", xsr);
				} else if ("title".equals(xsr.getLocalName())) {
					title = readTitle(xsr);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if ("entry".equals(xsr.getLocalName())) {
					if (bWord) {
						set.add(title);
					}
				}
			}
			}
		}
		
		var file = new File("xmls/stax/q11B.xml");
		file.createNewFile();
		var output = new FileOutputStream(file);

		Serializer s = new Processor().newSerializer();
		s.setOutputProperty(Property.METHOD, "xml");
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputStream(output);
		XMLStreamWriter writer = s.getXMLStreamWriter();
		
		writer.writeStartDocument();
		writer.writeStartElement("ul");
		
		set.forEach((str) -> {
			try {
				writer.writeStartElement("li");
				writer.writeCharacters(str);
				writer.writeEndElement();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		});
		
		writer.writeEndElement();
		writer.writeEndDocument();

		writer.close();
		output.close();
		fr.close();
		xsr.close();
	}

	private static String readTitle(XMLStreamReader reader) throws Exception {
		StringBuffer buffer = new StringBuffer();

		while (reader.hasNext()) {
			int event = reader.next();

			if (event == XMLStreamConstants.END_ELEMENT && "title".equals(reader.getLocalName())) {
				return buffer.toString().trim().replaceAll("\\s+", " ");
			}
			if (event == XMLStreamConstants.CHARACTERS) {
				buffer.append(reader.getText());
			}
		}

		throw new Exception();
	}

	private static boolean checkWord(String word, XMLStreamReader reader) throws Exception {
		StringBuffer buffer = new StringBuffer();

		while (reader.hasNext()) {
			int event = reader.next();

			if (event == XMLStreamConstants.END_ELEMENT && "body".equals(reader.getLocalName())) {
//				System.out.println(buffer);
				return buffer.indexOf(word) != -1;
			}
			if (event == XMLStreamConstants.CHARACTERS) {
				buffer.append(reader.getText());
			}
		}

		throw new Exception();
	}
}
