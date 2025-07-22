package unq.pdes._5.g1.segui_tus_compras.service.external;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import unq.pdes._5.g1.segui_tus_compras.model.external.RefreshToken;
import unq.pdes._5.g1.segui_tus_compras.repository.external.RefreshTokenRepository;

import java.util.Map;

@Component

public class MeLiApiKeyRefresher {

    private static final Logger logger = LoggerFactory.getLogger(MeLiApiKeyRefresher.class);
    private final RefreshTokenRepository refreshTokenRepository;
    private final RestClient restClient;

    @Getter
    private String accessToken;
    private final String clientId;
    private final String clientSecret;
    private final String envRefreshToken;
    private final Environment environment;

    public MeLiApiKeyRefresher(
            @Value("${mercadolibre.client.id}") String clientId,
            @Value("${mercadolibre.client.secret}") String clientSecret,
            @Value("${mercadolibre.api.url}") String apiUrl,
            @Value("${mercadolibre.api.refreshToken}") String envRefreshToken,
            RestClient.Builder restClientBuilder,
            RefreshTokenRepository refreshTokenRepository,
            Environment environment
    ){
        if (!StringUtils.hasText(apiUrl)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.api.url' is not set");
        }
        if (!StringUtils.hasText(clientId)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.client.id' is not set");
        }
        if (!StringUtils.hasText(clientSecret)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.client.secret' is not set");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restClient = restClientBuilder.baseUrl(apiUrl).build();
        this.refreshTokenRepository = refreshTokenRepository;
        this.envRefreshToken = envRefreshToken;
        this.environment = environment;
    }

    public void refreshAccessToken(String refreshToken) {
        try{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "refresh_token");
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("refresh_token", refreshToken);

            var response = restClient
                    .post()
                    .uri("/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(Map.class);

            this.accessToken = response.get("access_token").toString();
            String newRefreshToken = response.get("refresh_token").toString();
            logger.info("Access Key actualizada exitosamente:{}", accessToken);
            refreshTokenRepository.save(new RefreshToken(newRefreshToken));
            logger.info("Refresh Token actualizado exitosamente:{}", newRefreshToken);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PostConstruct
    public void init() {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("e2e".equals(profile)) {
                logger.info("Perfil 'e2e' activo, no se ejecuta scheduledRefresh en PostConstruct");
                return;
            }
        }
        scheduledRefresh();
    }

    @Scheduled(fixedRate = 21600000)
    public void scheduledRefresh() {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("e2e".equals(profile)) {
                return;
            }
        }
        String existingToken = refreshTokenRepository.findById(1L)
                .map(RefreshToken::getToken)
                .orElse(envRefreshToken);
        if (!StringUtils.hasText(existingToken)) {
            throw new IllegalStateException("Environment variable 'mercadolibre.refreshToken' is not set");
        }
        refreshAccessToken(existingToken);
    }
}
