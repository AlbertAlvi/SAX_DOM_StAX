package dom.questao_09;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListConverter {
	public static Collection<Node> toCollection(NodeList nodeList, Supplier<Collection<Node>> supplier) {
		return IntStream.range(0, nodeList.getLength())
				.mapToObj(nodeList::item)
				.collect(Collectors.toCollection(supplier));
	}
}
