package mk.ukim.finki.spotifyandchill.web.rest;


import mk.ukim.finki.spotifyandchill.model.SpotifyUserAuthorizationCode;
import mk.ukim.finki.spotifyandchill.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static mk.ukim.finki.spotifyandchill.service.utlis.RestCallUtilis.checkRequestParameterNotNull;

@RestController
@RequestMapping("/index/")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    SpotifyUserAuthorizationCode spotifyUserAuthorizationCode = new SpotifyUserAuthorizationCode();

    String authCode;

    @Autowired
    PlayerService playerService;

    @GetMapping("/authorize")
    public String authorize() {
        return authorizationService.grantApplicationAccess();
    }

    @GetMapping("/token")
    public JSONObject getToken() {
        JSONObject response = new JSONObject();
        if (spotifyUserAuthorizationCode.getCode() == null || spotifyUserAuthorizationCode.getCode().isEmpty()) {
            response.put("Error", "Application not authorized yet on user's behalf to access his data");
            return response;
        }
        JSONObject result = authorizationService.getToken(spotifyUserAuthorizationCode.getCode());
        spotifyUserAuthorizationCode.setAccessToken((String) result.get("access_token"));
        spotifyUserAuthorizationCode.setRefreshToken((String) result.get("refresh_token"));
        spotifyUserAuthorizationCode.setTokenType((String) result.get("token_type"));
        result.put("goToRecentlyPlayedLink", "http://localhost:8080/index/recentlyPlayed");
        return result;
    }


    @RequestMapping("/responseFromSpotify")
    public String authResponse(@RequestParam(required = false) String code, @RequestParam(required = false) String state, @RequestParam(required = false) String error) {
        if (error != null) {
            return "<h1>AccessGranted</h1> <br/>  state:" + state + "  error: " + error;
        }
        spotifyUserAuthorizationCode.setCode(code);
        spotifyUserAuthorizationCode.setUsername("user-" + Thread.currentThread().getName());
        return "<h1>AccessGranted</h1> <br/>  state:" + state + " <br/>  code:" + code + "<br/> <br/> getToken <a href=\"http://localhost:8080/index/token\">Get Token to interact on user's behalf</a>\n "; // TODO hide code display
    }

//    @GetMapping("/{name}")
//    public String hello(Model model, @PathVariable String name) {
//        checkRequestParameterNotNull("username", name);
//        return "Hello World! " + name;
//    }

    @GetMapping("/recentlyPlayed")
    public void getRecentPlayedTracks() {
        JSONObject response = new JSONObject();
        if (spotifyUserAuthorizationCode.getAccessToken() == null || spotifyUserAuthorizationCode.getAccessToken().isEmpty()) {
            response.put("Error", "UserAccessToken not fetched yet");
        }
        JSONObject result = playerService.getRecentlyPlayed(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        System.out.println(result);
    }
}
