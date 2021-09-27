package rj.bdt.routing.dto;

import java.util.List;

/**
 * Routing data object (payload).
 */
public class RoutingDto {

    private List<String> route;

    public RoutingDto(List<String> route) {
        this.route = route;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
