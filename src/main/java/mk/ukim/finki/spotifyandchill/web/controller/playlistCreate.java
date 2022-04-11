package mk.ukim.finki.spotifyandchill.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/playlistCreate")
public class playlistCreate {

    @GetMapping
    public String getCreatePlaylistPage(){
        return "playlistCreate";
    }
}
