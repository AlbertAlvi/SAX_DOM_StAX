package stax.questao_12;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.saxon.event.StreamWriterToReceiver;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXA {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/futebol.xml");
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		List<Jogo> jogos = new ArrayList<>(List.of(new Jogo()));
		var tempJogo = new Jogo();
		var bMandante = false;
//		var bVisitante = false;
		var bTime = false;
		var bGols = false;

		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if ("mandante".equals(reader.getLocalName())) {
					bMandante = true;
				} else if ("time".equals(reader.getLocalName())) {
					bTime = true;
				} else if ("gols".equals(reader.getLocalName())) {
					bGols = true;
				}
			}
			case XMLStreamConstants.CHARACTERS -> {
				if (bTime) {
					if (bMandante) {
						tempJogo.getMandante().setTime(reader.getText());
					} else {
						// visitante
						tempJogo.getVisitante().setTime(reader.getText());
					}
					bTime = false;
				} else if (bGols) {
					if (bMandante) {
						tempJogo.getMandante().setGols(reader.getText());
					} else {
						// visitante
						tempJogo.getVisitante().setGols(reader.getText());
					}
					bGols = false;
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if ("mandante".equals(reader.getLocalName())) {
					bMandante = false;
				} else if ("jogo".equals(reader.getLocalName())) {
					if (tempJogo.getSumGols() > jogos.get(0).getSumGols()) {
						jogos = new ArrayList<>(List.of(tempJogo));
					} else if (tempJogo.getSumGols() == jogos.get(0).getSumGols()) {
						jogos.add(tempJogo);
					}
					tempJogo = new Jogo();
				}
			}
			}
		}
		
		var s = new Processor().newSerializer(new File("xmls/stax/q12A.xml"));
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputProperty(Property.METHOD, "xml");
		XMLStreamWriter w = s.getXMLStreamWriter();
		
		w.writeStartDocument();
		w.writeStartElement("jogos");

		for(Jogo jogo : jogos) {
			w.writeStartElement("jogo");
			{
				w.writeStartElement("mandante");
				w.writeStartElement("time");
				w.writeCharacters(jogo.getMandante().getTime());
				w.writeEndElement(); // </time>
				w.writeStartElement("gols");
				w.writeCharacters(jogo.getMandante().getGols());
				w.writeEndElement(); // </gols>
				w.writeEndElement(); // </mandante>
			}
			{
				w.writeStartElement("visitante");
				w.writeStartElement("time");
				w.writeCharacters(jogo.getVisitante().getTime());
				w.writeEndElement(); // </time>
				w.writeStartElement("gols");
				w.writeCharacters(jogo.getVisitante().getGols());
				w.writeEndElement(); // </gols>
				w.writeEndElement(); // </visitante>
			}
			w.writeEndElement(); // </jogo>
		}
		
		w.writeEndElement(); // </jogos>
		w.writeEndDocument();

		w.close();
		fr.close();
		reader.close();
	}

	private static class Jogo {
		private TipoTime mandante;
		private TipoTime visitante;

		public Jogo() {
			mandante = new TipoTime();
			visitante = new TipoTime();
		}

		public TipoTime getMandante() {
			return mandante;
		}

		public TipoTime getVisitante() {
			return visitante;
		}

		public Integer getSumGols() {
			try {
				return Integer.parseInt(mandante.getGols()) + Integer.parseInt(visitante.getGols());
			} catch (Exception e) {
				return 0;
			}
		}

		@Override
		public String toString() {
			return "Jogo [mandante=" + mandante + ", visitante=" + visitante + "]";
		}

	}

	private static class TipoTime {
		private String time;
		private String gols;

		@Override
		public String toString() {
			return "TipoTime [time=" + time + ", gols=" + gols + "]";
		}

		public String getTime() {
			return time;
		}

		public String getGols() {
			return gols;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public void setGols(String gols) {
			this.gols = gols;
		}

	}
}
