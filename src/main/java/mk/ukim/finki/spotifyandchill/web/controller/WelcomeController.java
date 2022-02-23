package mk.ukim.finki.spotifyandchill.web.controller;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.json.simple.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String getWelcomePage(){
        return "welcome";
    }
}
