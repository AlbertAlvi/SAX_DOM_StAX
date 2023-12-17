package stax.questao_11;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXE {
	public static void main(String[] args) throws Exception {
		FileReader fileR = new FileReader("./src/main/resources/chalmers-biography-extract.xml");
		var reader = XMLInputFactory.newInstance().createXMLStreamReader(fileR);

		// key = birthPlace
		Map<String, List<Person>> map = new TreeMap<>();

		var tempPerson = new Person();
		var bNullBirthPlace = false;
		
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if ("entry".equals(reader.getLocalName())) {
					var birthPlace = reader.getAttributeValue(null, "birthplace");
					if(birthPlace == null) 
					{
						bNullBirthPlace = true;
						break;
					}
					tempPerson.setBirthPlace(birthPlace);
					tempPerson.setBorn(reader.getAttributeValue(null, "born"));
					tempPerson.setDied(reader.getAttributeValue(null, "died"));
					
				} else if("title".equals(reader.getLocalName())) {
					var name = getTitleText(reader);
					tempPerson.setName(name);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if ("entry".equals(reader.getLocalName())) {
					if(!bNullBirthPlace) {						
						List<Person> list = map.get(tempPerson.getBirthPlace());
						if(list != null) {
							list.add(tempPerson);
						} else {
							list = new ArrayList<>(List.of(tempPerson));
						}
						map.put(tempPerson.getBirthPlace(), list);
					}
					
					tempPerson = new Person();
					bNullBirthPlace = false;
				}
			}
			}
		}

		var s = new Processor().newSerializer(new File("xmls/stax/q11E.xml"));
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputProperty(Property.METHOD, "xml");
		var w = s.getXMLStreamWriter();
		
		w.writeStartDocument();
		w.writeStartElement("groups");
		
		for(List<Person> list : map.values()) {
			// sorting list by person name
			list.sort(Comparator.comparing(Person::getName));
			
			w.writeStartElement("group");
			w.writeAttribute("birthplace", list.get(0).getBirthPlace());
			
			for(Person p : list) {
				w.writeStartElement("person");
				w.writeAttribute("born", p.getBorn());
				w.writeAttribute("died", p.getDied());
				w.writeCharacters(p.getName());
				w.writeEndElement();
			}
			
			w.writeEndElement(); // </group>
		}
		
		w.writeEndElement();
		w.writeEndDocument();
		w.close();
		
		fileR.close();
		reader.close();
	}
	
	private static String getTitleText(XMLStreamReader reader) throws Exception {
		var buffer = new StringBuffer();
		
		while(reader.hasNext()) {
			int evt = reader.next();
			
			if(evt == XMLStreamConstants.END_ELEMENT && "title".equals(reader.getLocalName())) {
				return buffer.toString().trim().replaceAll("\\s+", " ");
			} 
			if(evt == XMLStreamConstants.CHARACTERS) {
				buffer.append(reader.getText());
			}
		}
		
		throw new Exception("Title end not found");
	}

	private static class Person {
		private String born;
		private String died;
		private String name;
		private String birthPlace;

		public String getBorn() {
			return born;
		}

		public String getDied() {
			return died;
		}

		public String getName() {
			return name;
		}

		public String getBirthPlace() {
			return birthPlace;
		}

		public void setBorn(String born) {
			this.born = born;
		}

		public void setDied(String died) {
			this.died = died;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setBirthPlace(String birthPlace) {
			this.birthPlace = birthPlace;
		}

	}
}
