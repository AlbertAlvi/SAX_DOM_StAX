package jaxb.futebol_jaxb;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cb")
@XmlAccessorType(XmlAccessType.FIELD)
public class Clube {
	@XmlElement(name = "rodada")
	private List<Rodada> rodadas;

	@Override
	public String toString() {
		return rodadas.stream()
				.map((rodada) -> rodada.toString())
				.reduce((a, b) -> a + "\n" + b)
				.get();
	}
}

@XmlAccessorType(XmlAccessType.FIELD)
class Rodada {
	@XmlAttribute
	private int n;
	@XmlElement(name = "jogo")
	private List<Jogo> jogos;

	@Override
	public String toString() {
		return "Rodada [n=" + n + ", jogos=" + jogos + "]";
	}
}

@XmlAccessorType(XmlAccessType.FIELD)
class Jogo {
	@XmlElement(name = "mandante")
	private MandanteOuVisitante mandante;
	@XmlElement(name = "visitante")
	private MandanteOuVisitante visitante;

	@Override
	public String toString() {
		return "Jogo [mandante=" + mandante + ", visitante=" + visitante + "]";
	}
}

@XmlAccessorType(XmlAccessType.FIELD)
class MandanteOuVisitante {
	private String time;
	private int gols;

	@Override
	public String toString() {
		return " [time=" + time + ", gols=" + gols + "]";
	}
}
