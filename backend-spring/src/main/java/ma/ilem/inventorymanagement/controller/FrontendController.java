package ma.ilem.inventorymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping("angular")
    public String angular() {
        return "forward:angular/index.html";
    }

    @GetMapping("vue")
    public String vue() {
        return "forward:vue/index.html";
    }
}
