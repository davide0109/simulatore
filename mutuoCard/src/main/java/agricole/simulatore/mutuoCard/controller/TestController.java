package agricole.simulatore.mutuoCard.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test Controller", description = "Api per la gestione dei test.")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
}