package unq.pdes._5.g1.segui_tus_compras.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import unq.pdes._5.g1.segui_tus_compras.exception.external.ExternalApiException;
import unq.pdes._5.g1.segui_tus_compras.exception.external.InvalidApiTokenException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ExternalProductDto;
import org.springframework.util.StringUtils;

@Service
public class MeLiApiService {

    private final RestClient restClient;
    private final String apiToken;

    public MeLiApiService(
            RestClient.Builder restClientBuilder,
            @Value("${mercadolibre.api.url}") String apiUrl,
            @Value("${mercadolibre.api.most.recent.token}") String apiToken
    ) {
        // Check if environment variables are properly set
        if (!StringUtils.hasText(apiUrl)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.api.url' is not set");
        }
        if (!StringUtils.hasText(apiToken)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.api.most.recent.token' is not set");
        }
        
        this.restClient = restClientBuilder.baseUrl(apiUrl).build();
        this.apiToken = apiToken;
    }

    public ExternalProductDto getProductById(String productId) {
        try {
            ResponseEntity<ExternalProductDto> response = restClient.get()
                    .uri("/products/" + productId)
                    .header("Authorization", "Bearer " + apiToken)
                    .retrieve()
                    .toEntity(ExternalProductDto.class);

            if (response.getStatusCode().value() == 200) {
                return response.getBody();
            } else {
                throw new ExternalApiException("Unexpected status code: " + response.getStatusCode().value());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                throw new InvalidApiTokenException("Invalid API token for MercadoLibre API");
            }
            throw new ExternalApiException("Error calling MercadoLibre API: " + e.getMessage());
        }
    }

    public ApiSearchDto search(String keywords, Integer offset, Integer limit) {
        try {
            ResponseEntity<ApiSearchDto> response = restClient.get()
                    .uri("/products/search?status=active&offset="+ offset +"&limit=" + limit + "&site_id=MLA&q=" + keywords)
                    .header("Authorization", "Bearer " + apiToken)
                    .retrieve()
                    .toEntity(ApiSearchDto.class);

            if (response.getStatusCode().value() == 200) {
                return response.getBody();
            } else {
                throw new ExternalApiException("Unexpected status code: " + response.getStatusCode().value());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                throw new InvalidApiTokenException("Invalid API token for MercadoLibre API");
            }
            throw new ExternalApiException("Error calling MercadoLibre API: " + e.getMessage());
        }
    }
}
