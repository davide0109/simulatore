package agricole.simulatore.mutuoCard.utils;

import agricole.simulatore.mutuoCard.utils.exception.UnexpectedTypeException;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private Map<String, String> keyProperties;

    private String MK;

    @Value("${constant.key-properties.ck-key}")
    private String CK_KEY;

    @Value("${constant.key-properties.cs-key}")
    private String CS_KEY;

    @Value("${constant.api.wso2-auth}")
    private String WSO2_API_AUTH;

    @Value("${constant.api.wso2-output}")
    private String WSO2_API_OUTPUT;

    public Map<String, String> getKeyProperties() {
        try {
            Context context = new InitialContext();
            FileInputStream file = null;
            Object url = context.lookup("url/wso2_session_properties_filepath");
            if (url instanceof String) {
                file = new FileInputStream((String) url);
            } else {
                URL url1 = (URL) url;
                file = new FileInputStream(url1.getPath());
            }

            int content;
            StringBuffer sb = new StringBuffer();
            while ((content = file.read()) != -1) {
                // Convert byte to character and print
                sb.append((char) content);
            }
            file.close();
            keyProperties = new HashMap<>();

            // Pattern generico per chiavi che iniziano con T1| e continuano fino al primo "="
            Pattern pattern = Pattern.compile("(T1\\|[^=]+) = (\\S+)");
            Matcher matcher = pattern.matcher(sb.toString());

            while (matcher.find()) {
                String key = matcher.group(1).trim();
                String value = matcher.group(2).trim();
                keyProperties.put(key, value);
            }
            return keyProperties;
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String getMk() {
        MK = System.getProperty("MK");
        return MK;
    }

    public String getWso2TokenPath() throws NamingException {
        Context context = new InitialContext();
        Object url = context.lookup("UrlWso2Token");
        if (url instanceof String) {
            return String.valueOf(url);
        } else if (url instanceof URL) {
            URL url1 = (URL) url;
            return url1.toString();
        } else throw new UnexpectedTypeException(url);
    }

    public String getWso2OutputPath() throws NamingException {
        Context context = new InitialContext();
        Object url = context.lookup("UrlWso2Output");
        if (url instanceof String) {
            return String.valueOf(url);
        } else if (url instanceof URL) {
            URL url1 = (URL) url;
            return url1.toString();
        } else throw new UnexpectedTypeException(url);
    }
}