package agricole.simulatore.mutuoCard.service;

import agricole.simulatore.mutuoCard.dto.response.Wso2AuthResponse;
import agricole.simulatore.mutuoCard.utils.ApplicationConstant;
import agricole.simulatore.mutuoCard.utils.CryptoAPIUtility;
import agricole.simulatore.mutuoCard.utils.exception.WebClient400Exception;
import agricole.simulatore.mutuoCard.utils.exception.WebClient500Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;

import reactor.core.publisher.Mono;

import javax.xml.bind.DatatypeConverter;

import java.util.Base64;

@Service
public class WebClientService {

    @Autowired
    ApplicationConstant applicationConstant;

    private final WebClient webClient;

    /**
     * Costruisce un oggetto WebClientService utilizzando il builder di WebClient fornito.
     *
     * @param webClientBuilder il builder WebClient utilizzato per creare il WebClient
     */
    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Effettua una richiesta POST all'endpoint specificato utilizzando WebClient.
     * Richiesta Token di auth a Wso2
     *
     * @param responseType il tipo di classe di risposta attesa dalla richiesta
     * @param <T>          il tipo generico della classe di risposta attesa
     * @return il risultato della richiesta POST deserializzato nel tipo specificato
     * @throws WebClient400Exception se si verifica un errore durante la chiamata all'endpoint
     * @throws RuntimeException      se si verifica un errore generico durante la chiamata HTTP
     */
    public <T> T postWso2Token(Class<T> responseType) {
        try {
            return webClient.post()
                .uri(applicationConstant.getWSO2_API_AUTH())
                .header("Authorization", "Basic " + getAuthorizationBasic())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    if (response.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                        return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new WebClient400Exception("Client error 400: Invalid Consumer Key... " + body)));
                    } else if (response.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new WebClient400Exception("Client error 401: Invalid Consumer Secret... " + body)));
                    }
                    return response.createException().flatMap(Mono::error);
                })
                .onStatus(HttpStatus::is5xxServerError, response -> response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new WebClient500Exception("Server error: " + body))))
                .bodyToMono(responseType)
                .block();
        } catch (WebClient400Exception | WebClient500Exception ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Si è verificato un errore durante il trasferimento dati a TM: " + e.getMessage(), e);
        }
    }

    /**
     * Effettua una richiesta POST all' endpoint specificato utilizzando WebClient.
     * Invio dati di Output a TM tramite api Wso2
     *
     * @param requestBody  il corpo della richiesta
     * @param responseType il tipo di classe di risposta attesa dalla richiesta
     * @param authResponse classe contenente il Bearer token fornito dalla chiamata di auth a Wso2
     * @param <T>          il tipo generico della classe di risposta attesa
     * @return il risultato della richiesta POST deserializzato nel tipo specificato
     * @throws WebClient400Exception se si verifica un errore durante la chiamata all'endpoint
     * @throws RuntimeException      se si verifica un errore generico durante la chiamata HTTP
     */
    public <T> T postWso2Output(Object requestBody, Class<T> responseType, Wso2AuthResponse authResponse) {
        try {
            return webClient.post()
                .uri(applicationConstant.getWSO2_API_OUTPUT())
                .header("Authorization", "Bearer " + authResponse.getAccessToken())
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new WebClient500Exception("Server error: " + body))))
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    if (response.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                        return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new WebClient400Exception("Output Api Client error 400: BAD_REQUEST... " + body)));
                    } else if (response.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new WebClient400Exception("Output Api Client error 401: UNAUTHORIZED... " + body)));
                    }
                    return response.createException().flatMap(Mono::error);
                })
                .bodyToMono(responseType)
                .block();
        } catch (WebClient400Exception | WebClient500Exception ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("Si è verificato un errore durante il trasferimento dati a TM: " + e.getMessage(), e);
        }
    }

    public String getAuthorizationBasic() throws Exception {
        String ck_cai = applicationConstant.getKeyProperties(applicationConstant.getCK_KEY());
        String cs_cai = applicationConstant.getKeyProperties(applicationConstant.getCS_KEY());
        String MK = applicationConstant.getMk();

        String initVectorCk = ck_cai.split(":")[0].trim();
        String initVectorCs = cs_cai.split(":")[0].trim();

        String ck = ck_cai.split(":")[1].trim();
        String cs = cs_cai.split(":")[1].trim();

        String CK = CryptoAPIUtility.decryptAsString_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(MK), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorCk), DatatypeConverter.parseBase64Binary(ck));
        String CS = CryptoAPIUtility.decryptAsString_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(MK), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorCs), DatatypeConverter.parseBase64Binary(cs));
        String basic = String.format("%s:%s", CK, CS);
        return Base64.getEncoder().encodeToString(basic.getBytes());
    }
}