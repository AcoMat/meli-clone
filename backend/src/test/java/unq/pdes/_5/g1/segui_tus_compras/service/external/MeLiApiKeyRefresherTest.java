package unq.pdes._5.g1.segui_tus_compras.service.external;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import unq.pdes._5.g1.segui_tus_compras.model.external.RefreshToken;
import unq.pdes._5.g1.segui_tus_compras.repository.external.RefreshTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeLiApiKeyRefresherTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @Mock
    private Environment environment;

    private MeLiApiKeyRefresher meLiApiKeyRefresher;

    private final String CLIENT_ID = "test-client-id";
    private final String CLIENT_SECRET = "test-client-secret";
    private final String API_URL = "https://api.mercadolibre.com";
    private final String ENV_REFRESH_TOKEN = "env-refresh-token";

    @BeforeEach
    void setUp() {
        when(restClientBuilder.baseUrl(API_URL)).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);

        meLiApiKeyRefresher = new MeLiApiKeyRefresher(
                CLIENT_ID,
                CLIENT_SECRET,
                API_URL,
                ENV_REFRESH_TOKEN,
                restClientBuilder,
                refreshTokenRepository,
                environment
        );
    }

    @Test
    void constructor_ShouldThrowException_WhenApiUrlIsEmpty() {
        assertThrows(IllegalStateException.class, () -> new MeLiApiKeyRefresher(
                CLIENT_ID,
                CLIENT_SECRET,
                "", // URL vacía
                ENV_REFRESH_TOKEN,
                restClientBuilder,
                refreshTokenRepository,
                environment
        ));
    }

    @Test
    void constructor_ShouldThrowException_WhenClientIdIsEmpty() {
        assertThrows(IllegalStateException.class, () -> new MeLiApiKeyRefresher(
                "", // Client ID vacío
                CLIENT_SECRET,
                API_URL,
                ENV_REFRESH_TOKEN,
                restClientBuilder,
                refreshTokenRepository,
                environment
        ));
    }

    @Test
    void constructor_ShouldThrowException_WhenClientSecretIsEmpty() {
        assertThrows(IllegalStateException.class, () -> new MeLiApiKeyRefresher(
                CLIENT_ID,
                "", // Client Secret vacío
                API_URL,
                ENV_REFRESH_TOKEN,
                restClientBuilder,
                refreshTokenRepository,
                environment
        ));
    }

    @Test
    void refreshAccessToken_ShouldUpdateTokens_WhenApiCallIsSuccessful() {
        // Arrange
        String testRefreshToken = "test-refresh-token";
        String newAccessToken = "new-access-token";
        String newRefreshToken = "new-refresh-token";

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("access_token", newAccessToken);
        apiResponse.put("refresh_token", newRefreshToken);

        // Mock de la cadena de llamadas del RestClient
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/oauth/token")).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(Map.class)).thenReturn(apiResponse);

        // Act
        meLiApiKeyRefresher.refreshAccessToken(testRefreshToken);

        // Assert
        assertEquals(newAccessToken, meLiApiKeyRefresher.getAccessToken());
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void refreshAccessToken_ShouldHandleException_WhenApiCallFails() {
        // Arrange
        String testRefreshToken = "test-refresh-token";

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/oauth/token")).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        assertDoesNotThrow(() -> meLiApiKeyRefresher.refreshAccessToken(testRefreshToken));

        // Verificar que no se guarda ningún token cuando falla
        verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
    }

    @Test
    void init_ShouldCallScheduledRefresh_WhenNotE2EProfile() {
        // Arrange
        when(environment.getActiveProfiles()).thenReturn(new String[]{"dev", "test"});

        // Mock para scheduledRefresh
        when(refreshTokenRepository.findById(1L)).thenReturn(Optional.of(new RefreshToken(ENV_REFRESH_TOKEN)));

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("access_token", "test-access-token");
        apiResponse.put("refresh_token", "test-refresh-token");

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/oauth/token")).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(Map.class)).thenReturn(apiResponse);

        // Act
        meLiApiKeyRefresher.init();

        // Assert
        verify(refreshTokenRepository).findById(1L);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void init_ShouldNotCallScheduledRefresh_WhenE2EProfileIsActive() {
        // Arrange
        when(environment.getActiveProfiles()).thenReturn(new String[]{"e2e", "test"});

        // Act
        meLiApiKeyRefresher.init();

        // Assert
        verify(refreshTokenRepository, never()).findById(anyLong());
    }

    @Test
    void scheduledRefresh_ShouldUseExistingToken_WhenTokenExistsInRepository() {
        // Arrange
        String existingToken = "existing-refresh-token";
        when(environment.getActiveProfiles()).thenReturn(new String[]{"prod"});
        when(refreshTokenRepository.findById(1L)).thenReturn(Optional.of(new RefreshToken(existingToken)));

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("access_token", "new-access-token");
        apiResponse.put("refresh_token", "new-refresh-token");

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/oauth/token")).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(Map.class)).thenReturn(apiResponse);

        // Act
        meLiApiKeyRefresher.scheduledRefresh();

        // Assert
        verify(refreshTokenRepository).findById(1L);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void scheduledRefresh_ShouldUseEnvToken_WhenNoTokenInRepository() {
        // Arrange
        when(environment.getActiveProfiles()).thenReturn(new String[]{"prod"});
        when(refreshTokenRepository.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("access_token", "new-access-token");
        apiResponse.put("refresh_token", "new-refresh-token");

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/oauth/token")).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(Map.class)).thenReturn(apiResponse);

        // Act
        meLiApiKeyRefresher.scheduledRefresh();

        // Assert
        verify(refreshTokenRepository).findById(1L);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void scheduledRefresh_ShouldNotExecute_WhenE2EProfileIsActive() {
        // Arrange
        when(environment.getActiveProfiles()).thenReturn(new String[]{"e2e"});

        // Act
        meLiApiKeyRefresher.scheduledRefresh();

        // Assert
        verify(refreshTokenRepository, never()).findById(anyLong());
    }

    @Test
    void scheduledRefresh_ShouldThrowException_WhenNoRefreshTokenAvailable() {
        // Arrange
        when(environment.getActiveProfiles()).thenReturn(new String[]{"prod"});
        when(refreshTokenRepository.findById(1L)).thenReturn(Optional.empty());

        // Crear instancia con env refresh token vacío
        MeLiApiKeyRefresher refresherWithEmptyToken = new MeLiApiKeyRefresher(
                CLIENT_ID,
                CLIENT_SECRET,
                API_URL,
                "", // Token vacío
                restClientBuilder,
                refreshTokenRepository,
                environment
        );

        // Act & Assert
        assertThrows(IllegalStateException.class, refresherWithEmptyToken::scheduledRefresh);
    }
}