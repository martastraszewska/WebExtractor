package pl.straszewska.auction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping
public class HomeController {

    @GetMapping({"/","/home"})
    public String getHomePage(){

        log.info("Home page was rendered");
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){

        log.info("Login page was rendered");
        return "login";
    }
}
