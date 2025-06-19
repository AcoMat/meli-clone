package unq.pdes._5.g1.segui_tus_compras.service.external;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import unq.pdes._5.g1.segui_tus_compras.exception.external.ExternalApiException;
import unq.pdes._5.g1.segui_tus_compras.exception.product.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ExternalProductDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, SpringExtension.class})
class MeLiApiServiceTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.Builder restClientBuilder;

    private MeLiApiService meliApiService;

    @Value("${mercadolibre.api.url}")
    private String apiUrl;

    @Value("${mercadolibre.api.most.recent.token}")
    private String apiToken;

    @BeforeEach
    void setUp() {
        doReturn(restClientBuilder).when(restClientBuilder).baseUrl(anyString());
        doReturn(restClient).when(restClientBuilder).build();

        meliApiService = new MeLiApiService(restClientBuilder, apiUrl, apiToken);
    }

    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {
        // Arrange
        String productId = "MLA123456";
        ExternalProductDto expectedProduct = new ExternalProductDto();
        expectedProduct.setId(productId);

        // Using answer to mock the chained calls
        doAnswer(invocation -> {
            RestClient.RequestHeadersUriSpec<?> uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
            RestClient.RequestHeadersSpec<?> headersSpec = mock(RestClient.RequestHeadersSpec.class);
            RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

            doReturn(headersSpec).when(uriSpec).uri("/products/" + productId);
            doReturn(headersSpec).when(headersSpec).header(eq("Authorization"), eq("Bearer " + apiToken));
            doReturn(responseSpec).when(headersSpec).retrieve();
            doReturn(expectedProduct).when(responseSpec).body(ExternalProductDto.class);

            return uriSpec;
        }).when(restClient).get();

        // Act
        ExternalProductDto result = meliApiService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    void getProductById_shouldThrowProductNotFoundException_whenProductNotFound() {
        // Arrange
        String productId = "MLA999999";

        doAnswer(invocation -> {
            RestClient.RequestHeadersUriSpec<?> uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
            RestClient.RequestHeadersSpec<?> headersSpec = mock(RestClient.RequestHeadersSpec.class);

            doReturn(headersSpec).when(uriSpec).uri("/products/" + productId);
            doReturn(headersSpec).when(headersSpec).header(anyString(), anyString());
            doThrow(HttpClientErrorException.NotFound.class).when(headersSpec).retrieve();

            return uriSpec;
        }).when(restClient).get();

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> meliApiService.getProductById(productId));
    }

    @Test
    void getProductById_shouldThrowExternalApiException_whenApiCallFails() {
        // Arrange
        String productId = "MLA123456";

        doAnswer(invocation -> {
            RestClient.RequestHeadersUriSpec<?> uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
            RestClient.RequestHeadersSpec<?> headersSpec = mock(RestClient.RequestHeadersSpec.class);

            doReturn(headersSpec).when(uriSpec).uri("/products/" + productId);
            doReturn(headersSpec).when(headersSpec).header(anyString(), anyString());
            doThrow(RuntimeException.class).when(headersSpec).retrieve();

            return uriSpec;
        }).when(restClient).get();

        // Act & Assert
        assertThrows(ExternalApiException.class, () -> meliApiService.getProductById(productId));
    }

    @Test
    void search_shouldReturnResults_whenSearchIsSuccessful() {
        // Arrange
        String keywords = "smartphone";
        Integer offset = 0;
        Integer limit = 10;
        ApiSearchDto expectedResults = new ApiSearchDto();
        expectedResults.setKeywords(keywords);

        String expectedUri = "/products/search?status=active&offset=0&limit=10&site_id=MLA&q=smartphone";

        doAnswer(invocation -> {
            RestClient.RequestHeadersUriSpec<?> uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
            RestClient.RequestHeadersSpec<?> headersSpec = mock(RestClient.RequestHeadersSpec.class);
            RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

            doReturn(headersSpec).when(uriSpec).uri(expectedUri);
            doReturn(headersSpec).when(headersSpec).header(eq("Authorization"), eq("Bearer " + apiToken));
            doReturn(responseSpec).when(headersSpec).retrieve();
            doReturn(expectedResults).when(responseSpec).body(ApiSearchDto.class);

            return uriSpec;
        }).when(restClient).get();

        // Act
        ApiSearchDto result = meliApiService.search(keywords, offset, limit);

        // Assert
        assertNotNull(result);
        assertEquals(keywords, result.getKeywords());
    }

    @Test
    void search_shouldThrowExternalApiException_whenSearchFails() {
        // Arrange
        String keywords = "smartphone";
        Integer offset = 0;
        Integer limit = 10;

        String expectedUri = "/products/search?status=active&offset=0&limit=10&site_id=MLA&q=smartphone";

        doAnswer(invocation -> {
            RestClient.RequestHeadersUriSpec<?> uriSpec = mock(RestClient.RequestHeadersUriSpec.class);
            RestClient.RequestHeadersSpec<?> headersSpec = mock(RestClient.RequestHeadersSpec.class);

            doReturn(headersSpec).when(uriSpec).uri(expectedUri);
            doReturn(headersSpec).when(headersSpec).header(anyString(), anyString());
            doThrow(RuntimeException.class).when(headersSpec).retrieve();

            return uriSpec;
        }).when(restClient).get();

        // Act & Assert
        assertThrows(ExternalApiException.class, () -> meliApiService.search(keywords, offset, limit));
    }

    @Test
    void constructor_shouldThrowException_whenApiUrlIsEmpty() {
        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
            new MeLiApiService(restClientBuilder, "", apiToken));
    }

    @Test
    void constructor_shouldThrowException_whenApiTokenIsEmpty() {
        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
            new MeLiApiService(restClientBuilder, apiUrl, ""));
    }
}