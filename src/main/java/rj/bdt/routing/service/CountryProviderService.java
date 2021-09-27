package rj.bdt.routing.service;

import rj.bdt.routing.dto.CountryDto;

/**
 * Service definition for country data provider.
 *
 * @author rjez
 */
public interface CountryProviderService {

    /**
     * @return list of country data objects from 3rd party country service.
     */
    public CountryDto[] getCountryData();
}
