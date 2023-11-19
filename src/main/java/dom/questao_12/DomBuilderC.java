package dom.questao_12;

import java.io.File;
import java.io.StringReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DomBuilderC {
	
	public static void main(String[] args) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document oldDoc = builder.parse("./src/main/resources/futebol.xml");
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		HashSet<String> times = times(xpath, oldDoc);
		mapearTimes(times);
		preencherMapa(times, xpath, oldDoc);
		
		auxiliarSet.forEach((map) -> {
			orderedMapSet.add(map);
		});
		
		String tableString = 
		"<table>" +
		  "<tr>" +
		    "<th scope=\"col\">classificacao</th>" +
		    "<th scope=\"col\">nome</th>" +
		    "<th scope=\"col\">vitorias</th>" +
		    "<th scope=\"col\">derrotas</th>" +
		    "<th scope=\"col\">empates</th>" +
		    "<th scope=\"col\">gols-marcados</th>" +
		    "<th scope=\"col\">gols-sofridos</th>" +
		    "<th scope=\"col\">saldo-de-gols</th>" +
		    "<th scope=\"col\">pontos</th>" +
		  "</tr>" + 
		"</table>";
		Document newDoc = builder.parse(new InputSource(new StringReader(tableString)));
		
		prettyPrint(newDoc);
	}
	
	private static void prettyPrint(Document doc) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.setOutputProperty(OutputKeys.METHOD, "xml");
		
		File file = new File("./xmls/q12C_classificacao.xml");
		Element table = (Element) doc.getElementsByTagName("table").item(0);
		
		orderedMapSet.forEach((map) -> {
			DomBuilderC.classificacao++;
			
			Element tr = doc.createElement("tr");
			
			Element classificacao = doc.createElement("td");
			classificacao.setTextContent(DomBuilderC.classificacao.toString());
			tr.appendChild(classificacao);
			
			map.forEach((label, data) -> {
				Element element = doc.createElement("td");
				element.setTextContent(data.toString());
				tr.appendChild(element);
			}); 
			
			table.appendChild(tr);
		});
		
		tf.transform(new DOMSource(doc), new StreamResult(file));
	}
	
	private static void preencherMapa(HashSet<String> times, XPath xpath, Document doc) throws Exception {
		for (Map<String, String> map : auxiliarSet) {
			String time = map.get("time");
			
//			Gols marcados
			Integer golsMarcados = ((Double) xpath
					.compile("sum(//visitante[time = '%s']/gols) + sum(//mandante[time = '%s']/gols)"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			map.put("gols_marcados", golsMarcados.toString());
			
//			Gols sofridos
			Integer golsSofridos = ((Double) xpath
					.compile("sum(//visitante[time = '%s']/../mandante/gols) + sum(//mandante[time = '%s']/../visitante/gols)"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			map.put("gols_sofridos", golsSofridos.toString());
			
//			Empates
			Integer empates = ((Double) xpath
					.compile("count(//visitante[time = '%s']/parent::jogo[visitante/gols = mandante/gols]) + count(//mandante[time = '%s']/parent::jogo[visitante/gols = mandante/gols])"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			map.put("empates", empates.toString());
			
//			Vitorias
			Integer vitorias = ((Double) xpath
					.compile("count(//visitante[time = '%s']/parent::jogo[visitante/gols > mandante/gols]) + count(//mandante[time = '%s']/parent::jogo[visitante/gols < mandante/gols])"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			map.put("vitorias", vitorias.toString());
			
//			Derrotas
			Integer derrotas = ((Double) xpath
					.compile("count(//visitante[time = '%s']/parent::jogo[visitante/gols < mandante/gols]) + count(//mandante[time = '%s']/parent::jogo[visitante/gols > mandante/gols])"
							.formatted(time, time))
					.evaluate(doc, XPathConstants.NUMBER)).intValue();
			map.put("derrotas", derrotas.toString());
			
//			Saldo de Gols
			Integer golsSaldo = golsMarcados - golsSofridos;
			map.put("gols_saldo", golsSaldo.toString());
			
//			Pontos
			Integer pontos = vitorias*3 + empates*1;
			map.put("pontos", pontos.toString());
		}

	}
	
	private static void mapearTimes(HashSet<String> times) {
		for (String time : times) {
			auxiliarSet.add(objectify(time));
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
	
	private static Integer classificacao = 0;
	private static Set<Map<String, String>> auxiliarSet = new HashSet<>();
	private static Set<Map<String, String>> orderedMapSet = new TreeSet<>(new TimeComparator());

	private static Map<String, String> objectify(String time) {
		Map<String, String> map = new LinkedHashMap<>();
//		map.put("colocacao", null); unecessary
		map.put("time", time); 
		map.put("vitorias", null);
		map.put("derrotas", null);
		map.put("empates", null);
		map.put("gols_marcados", null);
		map.put("gols_sofridos", null);
		map.put("gols_saldo", null);
		map.put("pontos", null);
		return map;
	}
	
	private static class TimeComparator implements Comparator<Map<String, String>> {

	    @Override
	    public int compare(Map<String, String> o1, Map<String, String> o2) {
	        // descending sort
	    	Integer pontos1 = Integer.parseInt(o1.get("pontos"));
	    	Integer pontos2 = Integer.parseInt(o2.get("pontos"));
	        int pontosComparison = pontos2.compareTo(pontos1);
	        if (pontosComparison != 0) {
	            return pontosComparison;
	        }

	        // descending sort
	        Integer vitorias1 = Integer.parseInt(o1.get("vitorias"));
	    	Integer vitorias2 = Integer.parseInt(o2.get("vitorias"));
	        int vitoriasComparison = vitorias2.compareTo(vitorias1);
	        if (vitoriasComparison != 0) {
	            return vitoriasComparison;
	        }

	        // ascending sort
	        Integer derrotas1 = Integer.parseInt(o1.get("derrotas"));
	    	Integer derrotas2 = Integer.parseInt(o2.get("derrotas"));
	        return derrotas1.compareTo(derrotas2);
	    }
	}
}
