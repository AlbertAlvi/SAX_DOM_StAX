package utility_classes;

import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class NodeListConverter {
	public static Collection<Element> toElementCollection(NodeList nodeList, Supplier<Collection<Element>> supplier) {
		return IntStream.range(0, nodeList.getLength())
//				.mapToObj(nodeList::item)
				.mapToObj(i -> (Element) nodeList.item(i))
				.collect(Collectors.toCollection(supplier));
	}
	
}
