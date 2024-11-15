package agricole.simulatore.mutuoCard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularFallbackController {

    @GetMapping("/frontend/simulatore/**")
    public String redirectToIndex() {
        return "forward:/frontend/index.html";
    }
}