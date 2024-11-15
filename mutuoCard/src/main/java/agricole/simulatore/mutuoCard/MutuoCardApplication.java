package agricole.simulatore.mutuoCard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MutuoCardApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplication(MutuoCardApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MutuoCardApplication.class);
    }
}