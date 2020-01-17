package org.aizeeck.t.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomePageController {

    @GetMapping("/main")
    public String mainPage(Map<String, Object> model) {
        return "main";
    }

}
