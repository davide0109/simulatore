package agricole.simulatore.mutuoCard.controller;

import agricole.simulatore.mutuoCard.utils.ApplicationConstant;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test Controller", description = "Api per la gestione dei test.")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ApplicationConstant applicationConstant;

    @GetMapping("/test")
    public String test() {
        log.info("{} {} {} {} {}", applicationConstant.getCK_KEY(), applicationConstant.getCS_KEY(), applicationConstant.getWSO2_API_AUTH(), applicationConstant.getWSO2_API_OUTPUT(), applicationConstant.getMk());
        return applicationConstant.getCK_KEY() + applicationConstant.getCS_KEY() + applicationConstant.getWSO2_API_AUTH() + applicationConstant.getWSO2_API_OUTPUT() + applicationConstant.getMk();
    }
}