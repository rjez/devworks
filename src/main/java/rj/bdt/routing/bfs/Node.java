package rj.bdt.routing.bfs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Routing country node
 */
public class Node {

    private final String value;
    private final Set<Node> neighbors;

    public Node(String value) {
        this.value = value;
        this.neighbors = new HashSet<>();
    }

    public String getValue() {
        return value;
    }

    public Set<Node> getNeighbors() {
        return Collections.unmodifiableSet(neighbors);
    }

    public void connect(Node node) {
        if (this != node) {
            this.neighbors.add(node);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value.equals(node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}