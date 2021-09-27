package rj.bdt.routing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rj.bdt.routing.dto.RoutingDto;
import rj.bdt.routing.service.RoutingResolverService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/routing") // This means URL's start with /xxx (after Application path)
public class RoutingController {

    private final static Logger LOG = LoggerFactory.getLogger(RoutingController.class);
    private final static String PARAMETER_ORIGIN = "origin";
    private final static String PARAMETER_DESTINATION = "destination";

    private final RoutingResolverService routingService;

    @Autowired
    public RoutingController(RoutingResolverService routingService) {
        this.routingService = routingService;
    }

    /**
     * @param origin      origin country ISO Alpha 3 code
     * @param destination destination country ISO Alpha 3 code
     * @return routing (object) with array of ISO Alpha 3 codes
     */
    @GetMapping(path = "/{" + PARAMETER_ORIGIN + "}/{" + PARAMETER_DESTINATION + "}")
    @ResponseBody
    public ResponseEntity<RoutingDto> get(@PathVariable(value = PARAMETER_ORIGIN) String origin,
                                          @PathVariable(value = PARAMETER_DESTINATION) String destination) {
        LOG.debug(MessageFormat.format("Routing request from {0} to {1}  ", origin, destination));
        try {
            Optional<List<String>> routing = routingService.getRouting(origin, destination);
            return routing.map(strings -> ResponseEntity.ok(new RoutingDto(strings)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        } catch (Exception e) {
            LOG.error("Routing controller error.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}