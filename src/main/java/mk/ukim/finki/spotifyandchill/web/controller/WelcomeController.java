package mk.ukim.finki.spotifyandchill.web.controller;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.hibernate.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.json.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.*;

import javax.servlet.http.*;
import java.util.*;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String getWelcomePage(){
        return "welcome";
    }

    @GetMapping("/aboutUs")
    public String getAboutUsPage(){
        return "aboutUs";
    }

    @GetMapping("/errorPage")
    public String getErrorPage(){
        return "errorPage";
    }

    @GetMapping("/profile")
    public String getProfilePage(){
        return "profile";
    }

    @Autowired
    AuthorizationService authorizationService;

    SpotifyUserAuthorizationCode spotifyUserAuthorizationCode = new SpotifyUserAuthorizationCode();

    String authCode;

    @Autowired
    UserService userService;

    @Autowired
    ArtistService artistService;

    @Autowired
    PlayerService playerService;


    @GetMapping("/authorize")
    public String authorize() {
        return authorizationService.grantApplicationAccess();
    }

    @RequestMapping("/responseFromSpotify")
    public String authResponse(@RequestParam(required = false) String code, @RequestParam(required = false) String state, @RequestParam(required = false) String error, HttpSession session) {
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



        JSONObject topArtists = playerService.getTopArtists(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        ArrayList<LinkedHashMap> artistsList = (ArrayList<LinkedHashMap>) topArtists.get("items");
        List<Artist> savedArtists = new ArrayList<>();
        for (int i=0; i< artistsList.size(); i++){
            LinkedHashMap artist = artistsList.get(i);
            String artistId = (String) artist.get("id");
            String artistName = (String) artist.get("name");
            ArrayList<LinkedHashMap> artistArrayList = (ArrayList<LinkedHashMap>) artist.get("images");;
            String artistImageUrl = artistArrayList.get(0).get("url").toString();

            LinkedHashMap artistExternalUrlsHash = (LinkedHashMap) artist.get("external_urls");
            String artistSpotifyUrl = (String) artistExternalUrlsHash.get("spotify");

            Artist artistSave = new Artist(artistId, artistName, artistImageUrl, artistSpotifyUrl);
            savedArtists.add(artistService.save(artistSave));
        }
        //user.setArtists();
        User user = new User(id, displayName, country, imageUrl, spotifyUrl, savedArtists);
        this.userService.save(user);

        user = this.userService.getById(id);

        System.out.println(user.toString());
        session.setAttribute("user", user);
        session.setAttribute("token", spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        System.out.println("this is token" + session.getAttribute("token"));
        return "redirect:/profile";
        //System.out.println("Finished");
        //result.put("goToRecentlyPlayedLink", "http://localhost:8080/index/recentlyPlayed");
        //return "redirect:/profile";
        //return "<h1>AccessGranted</h1> <br/>  state:" + state + " <br/>  code:" + code + "<br/> <br/> getToken <a href=\"http://localhost:8080/index/token\">Get Token to interact on user's behalf</a>\n "; // TODO hide code display
    }

    @GetMapping("/createPlaylist")
    public String createPlaylistView(){
        return "playlistCreate";
    }

    @PostMapping("/createPlaylist")
    public String createPlaylist(@RequestBody String jsonString){
        System.out.println(jsonString);
        JsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> map = jsonParser.parseMap(jsonString);
        System.out.println(map);
        return "redirect:/profile";
    }

}
