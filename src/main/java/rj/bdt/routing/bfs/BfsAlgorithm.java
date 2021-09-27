package rj.bdt.routing.bfs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Breadth First Search Algorithm for country routing
 */
public class BfsAlgorithm {

    private static final Logger LOG = LoggerFactory.getLogger(BfsAlgorithm.class);

    public static Optional<List<String>> search(String value, Node start) {
        Map<String, List<String>> trackMap = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);

        Node currentNode;
        Set<Node> alreadyVisited = new HashSet<>();

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            LOG.debug("Visited node with value: {}", currentNode.getValue());

            alreadyVisited.add(currentNode);
            final Node cNode = currentNode;
            for (Node n : currentNode.getNeighbors()) {
                queue.add(n);
                LOG.debug("Added: " + n.getValue());
                // update routing list
                List<String> track = trackMap.get(cNode.getValue());
                track = (track == null) ? new ArrayList<>() : new ArrayList<>(track);
                if (!trackMap.containsKey(n.getValue())) {
                    track.add(n.getValue());
                    trackMap.put(n.getValue(), track);
                }
                if (n.getValue().equals(value)) {
                    // startpoint is missing in routing
                    track.add(0, start.getValue());
                    return Optional.of(track);
                }
            };
            queue.removeAll(alreadyVisited);
        }
        return Optional.empty();
    }

}