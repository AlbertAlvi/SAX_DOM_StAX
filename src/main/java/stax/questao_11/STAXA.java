package stax.questao_11;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXA {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/chalmers-biography-extract.xml");
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		Map<Integer, String> map = new TreeMap<>();

		var died = 0;
		var title = "";

		while (xsr.hasNext()) {
			switch (xsr.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if (xsr.getLocalName().equals("entry")) {
					died = Integer.parseInt(xsr.getAttributeValue(null, "died"));
				} else if (xsr.getLocalName().equals("title")) {
					title = readTextContent(xsr);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if (xsr.getLocalName().equals("entry")) {
					map.put(died, title);
				}
			}
			}
		}

		File file = new File("xmls/stax/q11A.xml");
		// making sure the file exists
		file.createNewFile();

		// for indenting
		Processor p = new Processor();
		Serializer s = p.newSerializer();
		s.setOutputProperty(Property.METHOD, "xml");
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputStream(new FileOutputStream(file));
		XMLStreamWriter xsw = s.getXMLStreamWriter();

		xsw.writeStartDocument();
		xsw.writeStartElement("results");

		map.forEach((n, str) -> {
			try {
				xsw.writeStartElement("dude");
				xsw.writeCharacters(String.format("%s (%d)", str, n));
				xsw.writeEndElement();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		});

		xsw.writeEndElement(); // </results>
		xsw.writeEndDocument();

		fr.close();
		xsr.close();
		xsw.close();
	}

	private static String readTextContent(XMLStreamReader reader) throws Exception {
		StringBuilder textContent = new StringBuilder();

		while (reader.hasNext()) {
			int event = reader.next();

			if (event == XMLStreamConstants.END_ELEMENT && "title".equals(reader.getLocalName())) {
				return textContent.toString().trim().replaceAll("\\s+", " ");
			} else if (event == XMLStreamConstants.CHARACTERS) {
				textContent.append(reader.getText());
			}
		}

		throw new Exception();
	}
}
