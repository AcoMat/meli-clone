package unq.pdes._5.g1.segui_tus_compras.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import unq.pdes._5.g1.segui_tus_compras.exception.external.ExternalApiException;
import unq.pdes._5.g1.segui_tus_compras.exception.external.InvalidApiTokenException;
import unq.pdes._5.g1.segui_tus_compras.exception.product.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ExternalProductDto;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MeLiApiService {

    private static final Logger logger = LoggerFactory.getLogger(MeLiApiService.class);
    private final RestClient restClient;
    private final MeLiApiKeyRefresher meliApiKeyRefresher;

    public MeLiApiService(
            RestClient.Builder restClientBuilder,
            @Value("${mercadolibre.api.url}") String apiUrl,
            MeLiApiKeyRefresher meliApiKeyRefresher
    ) {
        // Check if environment variables are properly set
        if (!StringUtils.hasText(apiUrl)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.api.url' is not set");
        }
        this.restClient = restClientBuilder.baseUrl(apiUrl).build();
        this.meliApiKeyRefresher = meliApiKeyRefresher;
    }

    public ExternalProductDto getProductById(String productId) {
        String uri = UriComponentsBuilder.fromPath("/products/{productId}")
                .buildAndExpand(productId)
                .toUriString();

        try {
            return executeRequest(uri, ExternalProductDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                logger.warn("Product not found in MercadoLibre API: {}", productId);
                throw new ProductNotFoundException(productId);
            }
            throw handleHttpClientError(e);
        }
    }

    public ApiSearchDto search(String keywords, Integer offset, Integer limit) {
        String uri = UriComponentsBuilder.fromPath("/products/search")
                .queryParam("status", "active")
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .queryParam("site_id", "MLA")
                .queryParam("q", keywords)
                .toUriString();

        try {
            return executeRequest(uri, ApiSearchDto.class);
        } catch (HttpClientErrorException e) {
            throw handleHttpClientError(e);
        }
    }

    private <T> T executeRequest(String uri, Class<T> responseType) {
        ResponseEntity<T> response = restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + meliApiKeyRefresher.getAccessToken())
                .retrieve()
                .toEntity(responseType);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ExternalApiException("Unexpected status code: " + response.getStatusCode().value());
        }
    }

    private RuntimeException handleHttpClientError(HttpClientErrorException e) {
        if (e.getStatusCode().value() == 401) {
            logger.error("Invalid API token for MercadoLibre API. Please check the configured token.");
            return new InvalidApiTokenException("Invalid API token for MercadoLibre API. Please check the configured token.");
        } else if (e.getStatusCode().value() == 400) {
            return new IllegalArgumentException("Bad request to MercadoLibre API: Invalid parameter");
        }
        return new ExternalApiException("Error calling MercadoLibre API: " + e.getStatusCode() + " - " + e.getMessage());
    }
}
