package dom.questao_12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class DomBuilderC {
	
	private static Map<String, Map<String, String>> ultimap = new HashMap<>();
//	private static Map<String, String> tempMap = null;

	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document oldDoc = builder.parse("./src/main/resources/futebol.xml");
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xpath.compile("//jogo")
				.evaluate(oldDoc, XPathConstants.NODESET);
		
		HashSet<String> times = times(xpath, oldDoc);
		criarTimes(times);
		golsMarcados(times, xpath, oldDoc);
		golsSofridos(times, xpath, oldDoc);
		empates(times, xpath, oldDoc);
	}
	
	private static void empates(HashSet<String> times, XPath xpath, Document doc) throws Exception {
		for (String time : times) {
			int vitorias = ((Double) xpath
					.compile("count(//visitante[time = '%s']/parent::jogo[visitante/gols = mandante/gols]) + count(//mandante[time = '%s']/parent::jogo[visitante/gols = mandante/gols])"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			
			ultimap.get(time).put("empates", String.valueOf(vitorias));
			System.out.println(ultimap.get(time));
		}
	}
	
	private static void golsSofridos(HashSet<String> times, XPath xpath, Document doc) throws Exception {
		for (String time : times) {
			int nGols = ((Double) xpath
					.compile("sum(//visitante[time = '%s']/../mandante/gols) + sum(//mandante[time = '%s']/../visitante/gols)"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			
			ultimap.get(time).put("gols_sofridos", String.valueOf(nGols));
		}
	}
	
	private static void golsMarcados(HashSet<String> times, XPath xpath, Document doc) throws Exception {
		for (String time : times) {
			int nGols = ((Double) xpath
					.compile("sum(//visitante[time = '%s']/gols) + sum(//mandante[time = '%s']/gols)"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			
			ultimap.get(time).put("gols_marcados", String.valueOf(nGols));
		}
	}
	
	private static void criarTimes(HashSet<String> times) {
		for (String time : times) {
			ultimap.put(time, objectify());
		}
	}
	
	private static HashSet<String> times(XPath xpath, Document doc) throws Exception {
		NodeList nodes = (NodeList) xpath.compile("//time")
				.evaluate(doc, XPathConstants.NODESET);
		HashSet<String> times = new HashSet<>();
		for (int i = 0; i < nodes.getLength(); i++) {
			times.add(nodes.item(i).getTextContent());
		}
		return times;
	}

// maybe a clever idea
	private static Map<String, String> objectify() {
		Map<String, String> map = new HashMap<>();
		map.put("colocacao", null);
		map.put("pontos", null);
		map.put("vitorias", null);
		map.put("derrotas", null);
		map.put("gols_marcados", null);
		map.put("gols_sofridos", null);
		map.put("gols_saldo", null);
		return map;
	}
}
