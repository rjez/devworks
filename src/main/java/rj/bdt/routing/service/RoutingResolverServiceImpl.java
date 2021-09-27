package rj.bdt.routing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rj.bdt.routing.bfs.BfsAlgorithm;
import rj.bdt.routing.bfs.Node;
import rj.bdt.routing.dto.CountryDto;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation of country data provider.
 *
 * @author rjez
 */
@Service
public class RoutingResolverServiceImpl implements RoutingResolverService {

    private final static Logger LOG = LoggerFactory.getLogger(RoutingResolverServiceImpl.class);
    private final CountryProviderService countryProviderService;

    @Autowired
    public RoutingResolverServiceImpl(CountryProviderService countryProviderService) {
        this.countryProviderService = countryProviderService;
    }

    /**
     * Creates country node and adds it to hashmap or uses existing one
     *
     * @param countryMap the map that holds country data by country code
     * @param code       the country code
     * @return new or existing country node
     */
    private Node getNode(Hashtable<String, Node> countryMap, String code) {
        Node node;
        if (countryMap.containsKey(code)) {
            node = countryMap.get(code);
        } else {
            node = new Node(code);
            countryMap.put(code, node);
            LOG.debug("Created and put into map.");
        }
        return node;
    }

    @Override
    public Optional<List<String>> getRouting(String origin, String destination) {
        // origin is the same as destination
        if (origin.equals(destination)) {
            return Optional.empty();
        }
        Hashtable<String, Node> countryMap = new Hashtable<>();
        for (CountryDto countryDto : countryProviderService.getCountryData()) {
            String countryCode = countryDto.getCca3();
            // create country node and add it to hashtable or use existing
            if (countryDto.getBorders().length > 0) {
                Node node = getNode(countryMap, countryCode);
                Arrays.stream(countryDto.getBorders()).parallel().forEach(nbCode -> {
                    // create neighbor node and add it to hashtable
                    node.connect(getNode(countryMap, nbCode));
                });
            }
            // no routing for country without neighbor
            else if ((origin.equals(countryCode) || destination.equals(countryCode))) {
                return Optional.empty();
            }
        }
        if (!countryMap.containsKey(origin) || !countryMap.containsKey(destination)) {
            return Optional.empty();
        }
        return BfsAlgorithm.search(destination, countryMap.get(origin));
    }

}
