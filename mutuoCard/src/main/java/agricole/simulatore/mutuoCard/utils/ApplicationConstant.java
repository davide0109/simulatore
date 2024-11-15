package agricole.simulatore.mutuoCard.utils;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
public class ApplicationConstant {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConstant.class);

    public String filePath = "file/";

    @Value("${constant.fileName.sussistenza}")
    public String fileNameSussistenza;

    @Value("${constant.key-properties.ck-key}")
    private String CK_KEY;

    @Value("${constant.key-properties.cs-key}")
    private String CS_KEY;

    @Value("${constant.api.wso2-auth}")
    private String WSO2_API_AUTH;

    @Value("${constant.api.wso2-output}")
    private String WSO2_API_OUTPUT;

    /**
     * Tale metodo restituisce il valore della chiave, passata in ingresso, presente nel file key.properties.
     *
     * @param hashMapKey nome della chiave (key)
     * @return value della chiave key.
     */
    public String getKeyProperties(String hashMapKey) {

        Map<String, String> keyProperties = new HashMap<>();

        // Pattern generico per chiavi che iniziano con T1| e continuano fino al primo "="
        Pattern pattern = Pattern.compile("(T1\\|[^=]+) = (\\S+)");
        Matcher matcher = pattern.matcher(getKeyPropertiesString());

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();
            keyProperties.put(key, value);
        }
        return keyProperties.get(hashMapKey);
    }

    /**
     * Tale metodo accede alla risorsa JNDI URL del file key.properties e ne restituisce il contenuto sotto forma di stringa.
     *
     * @return key.properties in stringa
     */
    public String getKeyPropertiesString() {
        try {
            Context context = new InitialContext();
            FileInputStream file;
            StringBuilder sb = new StringBuilder();
            int content;

            Object url = context.lookup("url/wso2_session_properties_filepath");

            if (url instanceof String) {
                file = new FileInputStream((String) url);
            } else {
                URL url1 = (URL) url;
                file = new FileInputStream(url1.getPath());
            }

            while ((content = file.read()) != -1) {
                // Convert byte to character and print
                sb.append((char) content);
            }

            file.close();
            return sb.toString();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Estrae da JVM il valore della chiave MK.
     *
     * @return variabile mk.
     */
    public String getMk() {
        return System.getProperty("MK");
    }
}