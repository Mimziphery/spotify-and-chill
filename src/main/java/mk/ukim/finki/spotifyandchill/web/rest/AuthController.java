package mk.ukim.finki.spotifyandchill.web.rest;


import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.service.*;
import mk.ukim.finki.spotifyandchill.repository.*;
import mk.ukim.finki.spotifyandchill.web.controller.*;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/fku")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    SpotifyUserAuthorizationCode spotifyUserAuthorizationCode = new SpotifyUserAuthorizationCode();

    String authCode;

    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;


    @GetMapping("/authorize")
    public String authorize() {
        return authorizationService.grantApplicationAccess();
    }

//    @GetMapping("/token")
//    public String getToken() {
//        JSONObject response = new JSONObject();
//        if (spotifyUserAuthorizationCode.getCode() == null || spotifyUserAuthorizationCode.getCode().isEmpty()) {
//            return "redirect:/error";
//        }
//        JSONObject result = authorizationService.getToken(spotifyUserAuthorizationCode.getCode());
//        spotifyUserAuthorizationCode.setAccessToken((String) result.get("access_token"));
//        spotifyUserAuthorizationCode.setRefreshToken((String) result.get("refresh_token"));
//        spotifyUserAuthorizationCode.setTokenType((String) result.get("token_type"));
//        //System.out.println("Finished");
//        //result.put("goToRecentlyPlayedLink", "http://localhost:8080/index/recentlyPlayed");
//        return "redirect:/profile";
//    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }


    @RequestMapping("/responseFromSpotify")
    public void authResponse(@RequestParam(required = false) String code, @RequestParam(required = false) String state, @RequestParam(required = false) String error) {
        if (error != null) {
            //return "<h1>AccessGranted</h1> <br/>  state:" + state + "  error: " + error;
        }
        spotifyUserAuthorizationCode.setCode(code);
        spotifyUserAuthorizationCode.setUsername("user-" + Thread.currentThread().getName());


        JSONObject response = new JSONObject();
        if (spotifyUserAuthorizationCode.getCode() == null || spotifyUserAuthorizationCode.getCode().isEmpty()) {
            //return "redirect:/error";
        }
        JSONObject result = authorizationService.getToken(spotifyUserAuthorizationCode.getCode());
        spotifyUserAuthorizationCode.setAccessToken((String) result.get("access_token"));
        spotifyUserAuthorizationCode.setRefreshToken((String) result.get("refresh_token"));
        spotifyUserAuthorizationCode.setTokenType((String) result.get("token_type"));

        //System.out.println("Finished");
        //result.put("goToRecentlyPlayedLink", "http://localhost:8080/index/recentlyPlayed");
        //return "redirect:/profile";
        //return "<h1>AccessGranted</h1> <br/>  state:" + state + " <br/>  code:" + code + "<br/> <br/> getToken <a href=\"http://localhost:8080/index/token\">Get Token to interact on user's behalf</a>\n "; // TODO hide code display
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
        System.out.println(response);
        JSONObject result = playerService.getRecentlyPlayed(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        JSONObject currentUser = playerService.getCurrentUser(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        String id = currentUser.get("id").toString();
        System.out.println(id);
        ArrayList<LinkedHashMap> arrayList = (ArrayList<LinkedHashMap>) currentUser.get("images");;
        String imageUrl = arrayList.get(0).get("url").toString();
        System.out.println(imageUrl);
        LinkedHashMap externalUrlsHash = (LinkedHashMap) currentUser.get("external_urls");
        String spotifyUrl = (String) externalUrlsHash.get("spotify");
        System.out.println(spotifyUrl);
        String displayName = (String) currentUser.get("display_name");
        System.out.println(displayName);
        String country = (String) currentUser.get("country");
        System.out.println(country);

        User user = new User(id, displayName, country, imageUrl, spotifyUrl);
        this.userService.save(user);

        List<User> list = this.userService.listAll();
    }
}
