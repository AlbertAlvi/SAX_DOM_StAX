package stax.questao_11;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXD {
	public static void main(String[] args) throws Exception {
		FileReader fileR = new FileReader("./src/main/resources/chalmers-biography-extract.xml");
		var reader = XMLInputFactory.newInstance().createXMLStreamReader(fileR);

		List<Pair> list = new ArrayList<>();

		int age = 0;
		String name = null;

		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if ("entry".equals(reader.getLocalName())) {
					int born = Integer.parseInt(reader.getAttributeValue(null, "born"));
					int died = Integer.parseInt(reader.getAttributeValue(null, "died"));
					age = died - born;
				} else if ("title".equals(reader.getLocalName())) {
					name = readTitleNames(reader);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if ("entry".equals(reader.getLocalName())) {
					if (age > 88) {
						list.add(new Pair(age, name));
					}
				}
			}
			}
		}

		var s = new Processor().newSerializer(new File("xmls/stax/q11D.xml"));
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputProperty(Property.METHOD, "xml");
		XMLStreamWriter w = s.getXMLStreamWriter();

		w.writeStartDocument();
		w.writeStartElement("people");

		list.sort(Comparator.comparing(Pair::getAge).reversed());
		list.forEach((pair) -> {
			try {
				w.writeStartElement("person");
				w.writeCharacters(String.format("%s (age: %d)", 
						pair.getName(), pair.getAge()));
				w.writeEndElement();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		});

		w.writeEndElement();
		w.writeEndDocument();

		w.close();
		fileR.close();
		reader.close();
	}

	private static String readTitleNames(XMLStreamReader reader) throws Exception {
		StringBuffer bu = new StringBuffer();

		while (reader.hasNext()) {
			int evt = reader.next();

			if (evt == XMLStreamConstants.END_ELEMENT && reader.getLocalName().equals("title")) {
				return bu.toString().trim().replaceAll("\\s+", " ");
			}
			if (evt == XMLStreamConstants.CHARACTERS) {
				bu.append(reader.getText());
			}
		}

		throw new Exception();
	}

	private static class Pair {
		private int age;
		private String name;

		public Pair(int num, String name) {
			this.age = num;
			this.name = name;
		}

		public int getAge() {
			return age;
		}
		
		public String getName() {
			return name;
		}
	}
}
