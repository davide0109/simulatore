package agricole.simulatore.mutuoCard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wso2AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;
}