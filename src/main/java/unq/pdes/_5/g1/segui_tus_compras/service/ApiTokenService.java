package unq.pdes._5.g1.segui_tus_compras.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import unq.pdes._5.g1.segui_tus_compras.exception.TokenRefreshException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.TokenResponseDTO;

@Service
public class ApiTokenService {

    private static final Logger logger = LoggerFactory.getLogger(ApiTokenService.class);
    private final WebClient webClient;

    @Value("${mercadolibre.api.client.id}")
    private String clientId;
    @Value("${mercadolibre.api.client.secret}")
    private String clientSecret;
    @Value("${mercadolibre.api.most.recent.token}")
    private String initialRefreshToken;

    private TokenResponseDTO currentToken;

    public ApiTokenService(WebClient.Builder webClientBuilder) {
        String MELI_TOKEN_URL = "https://api.mercadolibre.com";
        this.webClient = webClientBuilder.baseUrl(MELI_TOKEN_URL).build();
    }

    public String refreshToken() {
        //TODO: Check if the token is expired before refreshing
        String refreshToken = currentToken != null ? currentToken.getRefresh_token() : initialRefreshToken;
        try{
            currentToken = webClient.post()
                    .uri("/oauth/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret)
                            .with("refresh_token", refreshToken))
                    .retrieve()
                    .bodyToMono(TokenResponseDTO.class)
                    .block();
            logger.info("Token refreshed successfully");
            return currentToken.getAccess_token();
        } catch (Exception e) {
            logger.error("Error refreshing token: {}", e.getMessage());
            throw new TokenRefreshException(e.getMessage());
        }
    }

    public String getAccessToken() {
        if (currentToken == null) {
            logger.info("Refreshing token...");
            return refreshToken();
        }
        return currentToken.getAccess_token();
    }
}
