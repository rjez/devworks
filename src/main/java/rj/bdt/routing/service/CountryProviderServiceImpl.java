package rj.bdt.routing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rj.bdt.routing.dto.CountryDto;

/**
 * Service implementation of country data provider.
 *
 * @author rjez
 *
 */
@Service
public class CountryProviderServiceImpl implements CountryProviderService {

	private final static Logger LOG = LoggerFactory.getLogger(CountryProviderServiceImpl.class);
	private final String countryServiceUrl;

	public CountryProviderServiceImpl(@Value("${service.countries.endpoint}") String countryServiceUrl,
									  @Value("${service.countries.method.getall}") String countryServiceMethod) {
		this.countryServiceUrl = countryServiceUrl + countryServiceMethod;
	}

	@Override
	public CountryDto[] getCountryData() {

		HttpHeaders requestHeaders = new HttpHeaders();
		// requestHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
		requestHeaders.set(HttpHeaders.ACCEPT, "application/json");
		requestHeaders.set(HttpHeaders.ACCEPT_ENCODING, "GZIP");
		HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			ClientHttpResponse response = execution.execute(request,body);
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			return response;
		});
		try {
			ResponseEntity<CountryDto[]> resp = restTemplate.exchange(countryServiceUrl,
					HttpMethod.GET, requestEntity, CountryDto[].class);
			return resp.getBody();
		}
		catch (Exception e) {
			LOG.error("Couldn't get country data from url: " + countryServiceUrl);
			throw e;
		}
	}
}
