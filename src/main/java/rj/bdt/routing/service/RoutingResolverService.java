package rj.bdt.routing.service;

import java.util.List;
import java.util.Optional;

/**
 * Service definition for country data provider.
 * 
 * @author rjez
 *
 */
public interface RoutingResolverService {

    /**
     * @param origin country iso alpha 3 code
     * @param destination country iso alpha 3 code
     * @return routing as optional list of country codes
     */
    Optional<List<String>> getRouting(String origin, String destination);
}
