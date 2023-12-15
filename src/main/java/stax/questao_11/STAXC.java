package stax.questao_11;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Serializer.Property;

public class STAXC {
	public static void main(String[] args) throws Exception {
		FileReader fileR = new FileReader("./src/main/resources/chalmers-biography-extract.xml");
		var reader = XMLInputFactory.newInstance().createXMLStreamReader(fileR);

		StringBuffer buffer = new StringBuffer();
		Set<StringBuffer> set = new LinkedHashSet<>();
		var pCount = 0;
		
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT -> {
				if ("entry".equals(reader.getLocalName())) {
					pCount = 0;
					buffer = new StringBuffer();
					
				} else if("p".equals(reader.getLocalName())) {
					pCount++;
				}
				
				buffer.append("<"+reader.getLocalName());
				for(int i = 0; i < reader.getAttributeCount(); ++i) {
					buffer.append(String.format(" %s = \"%s\"",
							reader.getAttributeLocalName(i), 
							reader.getAttributeValue(i)));
				}
				buffer.append(">");
			}
			case XMLStreamConstants.CHARACTERS -> {
				buffer.append(reader.getText());
			}
			case XMLStreamConstants.END_ELEMENT -> {
				if ("entry".equals(reader.getLocalName())) {
					if(pCount >= 3) {
						set.add(buffer);						
					}
				}
				buffer.append("</" + reader.getLocalName() + ">");
			}
			}
		}
		
		File outputFile = new File("xmls/stax/q11C.xml");
		outputFile.createNewFile();
		
		Serializer s = new Processor().newSerializer();
		s.setOutputProperty(Property.METHOD, "text");
		s.setOutputProperty(Property.INDENT, "yes");
		s.setOutputStream(new FileOutputStream(outputFile));
		XMLStreamWriter w =	s.getXMLStreamWriter();
		
		w.writeStartDocument();
		w.writeCharacters("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		w.writeCharacters("<dictionary>\n");
		set.forEach((entry) -> {
			try {
				w.writeCharacters(entry.toString());
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		});
		w.writeCharacters("</dictionary>");
		w.writeEndDocument();
		
		w.close();
		reader.close();
		fileR.close();
	}
}
