package stax.questao_12;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXC {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("./src/main/resources/futebol.xml");
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(fr);

		Map<String, Time> map = new HashMap<>();

		boolean bGols = false;
		boolean bTime = false;
		boolean bMandante = false;
		int golsMandante = 0;
		int golsVisitante = 0;
		String nomeMandante = null;
		String nomeVisitante = null;

		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				var localName = reader.getLocalName();

				if ("gols".equals(localName)) {
					bGols = true;
				} else if ("time".equals(localName)) {
					bTime = true;
				} else if ("mandante".equals(localName)) {
					bMandante = true;
				}
			}
			case XMLStreamConstants.CHARACTERS -> {
				if (bGols) {
					if (bMandante) {
						golsMandante = Integer.parseInt(reader.getText());
					} else {
						golsVisitante = Integer.parseInt(reader.getText());
					}
					bGols = false;
				} else if (bTime) {
					if (bMandante) {
						nomeMandante = reader.getText();
					} else {
						nomeVisitante = reader.getText();
					}
					bTime = false;
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				var localName = reader.getLocalName();

				if ("mandante".equals(localName)) {
					bMandante = false;
				} else if ("jogo".equals(localName)) {
					// visitante
					{
						Time time = map.get(nomeVisitante);
						if (time == null) {
							time = new Time(nomeVisitante);
							map.put(nomeVisitante, time);
						}
						time.updateAttributes(golsVisitante, golsMandante);
					}

					// mandante
					{
						Time time = map.get(nomeMandante);
						if (time == null) {
							time = new Time(nomeMandante);
							map.put(nomeMandante, time);
						}
						time.updateAttributes(golsMandante, golsVisitante);
					}
				}
			}
			}
		}

		var s = new Processor().newSerializer(new File("xmls/stax/q12C.html"));
		s.setOutputProperty(Property.METHOD, "html");
		s.setOutputProperty(Property.INDENT, "yes");
		var w = s.getXMLStreamWriter();

		List<Time> list = new ArrayList<>(map.values());
		list.sort(new Comparator<Time>() {
			@Override
			public int compare(Time o1, Time o2) {
				int pontos1 = o1.getPontos();
		        int pontos2 = o2.getPontos();
		        if (pontos1 != pontos2) {
		            return pontos1 > pontos2 ? -1 : 1; // decrescente
		        }

		        int vitorias1 = o1.getVitorias();
		        int vitorias2 = o2.getVitorias();
		        if (vitorias1 != vitorias2) {
		            return vitorias1 > vitorias2 ? -1 : 1; // decrescente
		        }

		        int derrotas1 = o1.getDerrotas();
		        int derrotas2 = o2.getDerrotas();
		        return derrotas1 < derrotas2 ? -1 : 1;
			}
		});

		w.writeStartDocument();
		w.writeStartElement("table");
		w.writeAttribute("border", "1");
		w.writeStartElement("tr");

		String[] headers = { "classificacao", "nome", "vitorias", "derrotas", "empates", "gols-marcados",
				"gols-sofridos", "saldo-de-gols", "pontos" };
		for (String header : headers) {
			w.writeStartElement("th");
			w.writeAttribute("scope", "col");
			w.writeCharacters(header);
			w.writeEndElement();
		}
		w.writeEndElement();

		for (int i = 0; i < list.size(); ++i) {
			Time time = list.get(i);
			w.writeStartElement("tr");
			writeTableData(w, String.valueOf(i+1));
			writeTableData(w, time.getNome());
			writeTableData(w, String.valueOf(time.getVitorias()));
			writeTableData(w, String.valueOf(time.getDerrotas()));
			writeTableData(w, String.valueOf(time.getEmpates()));
			writeTableData(w, String.valueOf(time.getGolsMarcados()));
			writeTableData(w, String.valueOf(time.getGolsSofridos()));
			writeTableData(w, String.valueOf(time.getSaldoGols()));
			writeTableData(w, String.valueOf(time.getPontos()));
			w.writeEndElement();
		}

		w.writeEndElement();
		w.writeEndDocument();
		w.close();

		fr.close();
		reader.close();
	}

	private static void writeTableData(XMLStreamWriter w, String chars) throws Exception {
		w.writeStartElement("td");
		w.writeCharacters(chars);
		w.writeEndElement();
	}

	private static class Time {
		private String nome;
		private int vitorias;
		private int empates;
		private int derrotas;
		private int golsMarcados;
		private int golsSofridos;
//		private int saldoGols;
//		private int pontos;

		public Time(String nome) {
			this.nome = nome;
		}

		public void updateAttributes(int gols, int otherGols) {
			this.golsMarcados += gols;
			this.golsSofridos += otherGols;

			if (gols > otherGols) {
				this.vitorias++;
			} else if (gols < otherGols) {
				this.derrotas++;
			} else {
				this.empates++;
			}

		}

		@Override
		public String toString() {
			return "Time [Nome=" + nome + ", vitorias=" + vitorias + ", empates=" + empates + ", derrotas=" + derrotas
					+ ", golsMarcados=" + golsMarcados + ", golsSofridos=" + golsSofridos + "]";
		}

		public String getNome() {
			return nome;
		}

		public int getVitorias() {
			return vitorias;
		}

		public int getEmpates() {
			return empates;
		}

		public int getDerrotas() {
			return derrotas;
		}

		public int getPontos() {
			return this.vitorias * 3 + this.empates;
		}

		public int getGolsMarcados() {
			return golsMarcados;
		}

		public int getGolsSofridos() {
			return golsSofridos;
		}

		public int getSaldoGols() {
			return this.golsMarcados - this.golsSofridos;
		}

	}
}
