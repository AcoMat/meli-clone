package unq.pdes._5.g1.segui_tus_compras.model.dto;

public class TokenResponseDTO {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private Long user_id;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}