package unq.pdes._5.g1.segui_tus_compras.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import unq.pdes._5.g1.segui_tus_compras.exception.external.ExternalApiException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ExternalProductDto;
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
            return restClient.get()
                    .uri("/products/" + productId)
                    .header("Authorization", "Bearer " + apiToken)
                    .retrieve()
                    .body(ExternalProductDto.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ExternalApiException("Error fetching product with ID: " + productId);
        }
    }

    public ApiSearchDto search(String keywords, Integer offset, Integer limit) {
        try {
            return restClient.get()
                    .uri("/products/search?status=active&offset="+ offset +"&limit=" + limit + "&site_id=MLA&q=" + keywords)
                    .header("Authorization", "Bearer " + apiToken)
                    .retrieve()
                    .body(ApiSearchDto.class);
        } catch (Exception e) {
            throw new ExternalApiException("Error searching for products with keywords: " + keywords);
        }
    }
}
