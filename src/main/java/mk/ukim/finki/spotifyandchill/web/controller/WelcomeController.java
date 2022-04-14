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
    SongService songService;

    @Autowired
    PlaylistService playlistService;

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
        ArrayList<LinkedHashMap> arrayList = (ArrayList<LinkedHashMap>) currentUser.get("images");
        String imageUrl;
        if(arrayList.size() == 0) {
            imageUrl = "https://icon-library.com/images/no-user-image-icon/no-user-image-icon-3.jpg";
        }else{
            imageUrl = arrayList.get(0).get("url").toString();
        }
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
        if (artistsList.size() == 0){
            topArtists = playerService.getTopArtistsLongTerm(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
            artistsList = (ArrayList<LinkedHashMap>) topArtists.get("items");
        }
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
        User user = new User();

        List<Playlist> playlists;
        if(userService.getById(id).isPresent()){
            User userOld = userService.getById(id).get();
            playlists = userOld.getPlaylists();
            user.setPlaylists(playlists);
        }

        user.setId(id);
        user.setDisplayName(displayName);
        user.setCountry(country);
        user.setImageUrl(imageUrl);
        user.setSpotifyUrl(spotifyUrl);
        user.setArtists(savedArtists);
        this.userService.save(user);

        user = this.userService.getById(id).get();

        System.out.println(user.toString());
        session.setAttribute("user", user);
        session.setAttribute("token", spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        System.out.println("this is token" + session.getAttribute("token"));

        session.setAttribute("playlists", user.getPlaylists());

        return "redirect:/profile";
        //System.out.println("Finished");
        //result.put("goToRecentlyPlayedLink", "http://localhost:8080/index/recentlyPlayed");
        //return "redirect:/profile";
        //return "<h1>AccessGranted</h1> <br/>  state:" + state + " <br/>  code:" + code + "<br/> <br/> getToken <a href=\"http://localhost:8080/index/token\">Get Token to interact on user's behalf</a>\n "; // TODO hide code display
    }

    @GetMapping("/createPlaylist")
    public String createPlaylistView(HttpSession session){
        session.removeAttribute("playlist");
        return "playlistCreate";
    }

    @PostMapping("/createPlaylist")
    public String createPlaylist(@RequestBody String jsonString, HttpSession session){
        System.out.println(jsonString);
        JsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> map = jsonParser.parseMap(jsonString);
        String name = (String) map.get("name");
        String description = (String) map.get("description");
        List<String> songIds = new ArrayList<>();

        JSONObject createPlaylistBody = new JSONObject();
        createPlaylistBody.put("name", name);
        createPlaylistBody.put("description", description);

        User currentUser = (User) session.getAttribute("user");

        JSONObject playlist = playerService.postPlaylist(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken(), createPlaylistBody, currentUser.getId());

        System.out.println(playlist);
        songIds = (ArrayList<String>) map.get("songIds");
        JSONObject addSongsBody = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<songIds.size(); i++){
            String songUri = "spotify:track:" + songIds.get(i);
            jsonArray.add(i, songUri);
        }
        addSongsBody.put("uris", jsonArray);

        String playlistId = (String) playlist.get("id");
        playerService.addSongsToPlaylist(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken(), addSongsBody, playlistId );

        Playlist userPlaylist = new Playlist(playlistId);
        playlistService.save(userPlaylist);

        if(!currentUser.isPlaylistDefined()){
            currentUser.newPlaylistArray();
        }
        currentUser.appendPlaylist(userPlaylist);
        userService.save(currentUser);

        session.setAttribute("user", currentUser);
        return "redirect:/profile";
    }

    @GetMapping("/editPlaylist/{id}")
    public String editPlaylistView(@PathVariable String id, HttpSession session) throws Throwable {
        User user = (User) session.getAttribute("user");
        List<Playlist> playlists = user.getPlaylists();
        Playlist playlist = playlistService.getById(id);

        if(!playlists.contains(playlist)){
            throw new Throwable("Forbidden");
        }else{
            session.setAttribute("playlist", playlist);
            return "playlistCreate";
        }
    }

    @PostMapping("/editPlaylist/{id}")
    public String editPlaylist(@PathVariable String id, @RequestBody String jsonString, HttpSession session){
        System.out.println(jsonString);
        JsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> map = jsonParser.parseMap(jsonString);
        String name = (String) map.get("name");
        String description = (String) map.get("description");
        List<String> songIds = new ArrayList<>();

        JSONObject editPlaylistBody = new JSONObject();
        editPlaylistBody.put("name", name);
        editPlaylistBody.put("description", description);


        JSONObject playlist = playerService.editPlaylist(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken(), editPlaylistBody, id);

        System.out.println(playlist);
        songIds = (ArrayList<String>) map.get("songIds");
        JSONObject addSongsBody = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<songIds.size(); i++){
            String songUri = "spotify:track:" + songIds.get(i);
            jsonArray.add(i, songUri);
        }
        addSongsBody.put("uris", jsonArray);

        playerService.editSongsOfPlaylist(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken(), addSongsBody, id );

        return "redirect:/profile";
    }
}
