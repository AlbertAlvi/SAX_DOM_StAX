package stax.questao_12;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;

import net.sf.saxon.event.StreamWriterToReceiver;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXB {
	public static void main(String[] args) throws Exception {
		FileReader fileR = new FileReader("./src/main/resources/futebol.xml");
		var reader = XMLInputFactory.newInstance().createXMLStreamReader(fileR);

		List<Rodada> list = new ArrayList<>();
		Integer gols = 0;
		String n = null;
		var bGols = false;

		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if ("gols".equals(reader.getLocalName())) {
					bGols = true;
				} else if ("rodada".equals(reader.getLocalName())) {
					n = reader.getAttributeValue(null, "n");
				}
			}
			case XMLStreamConstants.CHARACTERS -> {
				if (bGols) {
					bGols = false;
					gols += Integer.parseInt(reader.getText());
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if ("rodada".equals(reader.getLocalName())) {
					list.add(new Rodada(gols, n));
					gols = 0;
					n = null;
				}
			}
			}
		}

		var s = new Processor().newSerializer(new File("xmls/stax/q12B.html"));
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputProperty(Property.METHOD, "html");
		StreamWriterToReceiver w = s.getXMLStreamWriter();

		w.writeStartDocument();
		w.writeStartElement("table");
		w.writeAttribute("border", "1");
		w.writeStartElement("tr");
		{
			w.writeStartElement("th");
			w.writeCharacters("Rodada");
			w.writeEndElement(); // </th>
			
			w.writeStartElement("th");
			w.writeCharacters("Gols");
			w.writeEndElement(); // </th>
		}		
		w.writeEndElement(); // </tr>

		list.sort(Comparator.comparing(Rodada::getGols).reversed());
		for (int i = 0; i < list.size(); ++i) {
			Rodada rodada = list.get(i);
			w.writeStartElement("tr");
			
			w.writeStartElement("td");
			w.writeCharacters(rodada.getN());
			w.writeEndElement();
			
			w.writeStartElement("td");
			w.writeCharacters(rodada.getGols().toString());
			w.writeEndElement();
			
			w.writeEndElement(); // </tr>
		}

		w.writeEndElement();
		w.writeEndDocument();
		w.close();

		fileR.close();
		reader.close();
	}

	private static class Rodada {
		private Integer gols;
		private String n;

		public Rodada(Integer gols, String n) {
			this.gols = gols;
			this.n = n;
		}

		public Integer getGols() {
			return gols;
		}

		public String getN() {
			return n;
		}
	}
}
