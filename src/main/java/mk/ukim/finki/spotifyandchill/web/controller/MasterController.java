package mk.ukim.finki.spotifyandchill.web.controller;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.json.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;


@Controller
@RequestMapping("/")
public class MasterController {

    @Autowired
    AuthorizationService authorizationService;

    SpotifyUserAuthorizationCode spotifyUserAuthorizationCode = new SpotifyUserAuthorizationCode();

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

    @Autowired
    AlbumService albumService;

    @GetMapping("/")
    public String getWelcomePage(HttpSession session){
        List<User> users = this.userService.listAll();
        User user = (User) session.getAttribute("user");
        if(!users.isEmpty() && user!=null)
            session.setAttribute("users", users);

        return "login";
    }
    @RequestMapping(value = "/{username}")
    public  String returnUserProfilePage(@PathVariable String username, HttpSession session){
        Optional<User> selectedUser = this.userService.findByUsername(username);
        User user = (User) session.getAttribute("user");
        if(selectedUser.isPresent()){
            if(selectedUser.get().getDisplayName().equals(user.getDisplayName()))
                return "redirect:/profile";
            session.setAttribute("selectedUser", selectedUser.get());
            return "selectedProfile";
        }
        return "redirect:/";
    }

    @GetMapping("/aboutUs")
    public String getAboutUsPage(){
        return "aboutUs";
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return "error";
    }

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

        JSONObject topAlbums = playerService.getAlbums(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        ArrayList<LinkedHashMap> albumsList = (ArrayList<LinkedHashMap>) topAlbums.get("items");
        if (albumsList.size() == 0){
            //DISPLAY MESSAGE
            return "redirect:/profile";
        }
        List<Album> savedAlbums = new ArrayList<>();
        for (int i=0; i< albumsList.size(); i++){
            LinkedHashMap album = albumsList.get(i);

            LinkedHashMap curralbum = (LinkedHashMap) album.get("album");

            String albumId = (String) curralbum.get("id");

            System.out.println("albumId: " + albumId);


            String albumName = (String) curralbum.get("name");

            ArrayList<LinkedHashMap> albumsArtistsArrayList = (ArrayList<LinkedHashMap>) curralbum.get("artists");
            ArrayList<LinkedHashMap> albumsImagesArrayList = (ArrayList<LinkedHashMap>) curralbum.get("images");
            String albumImageUrl = albumsImagesArrayList.get(0).get("url").toString();
            String albumAristName = albumsArtistsArrayList.get(0).get("name").toString();

            LinkedHashMap albumExternalUrlsHash = (LinkedHashMap) curralbum.get("external_urls");
            String albumSpotifyUrl = (String) albumExternalUrlsHash.get("spotify");


            Album albumSave = new Album(albumId, albumName, albumAristName, albumImageUrl, albumSpotifyUrl);

            savedAlbums.add(albumService.save(albumSave));
        }

        User user = new User();

        List<Playlist> playlists;
        if(userService.getById(id).isPresent()){
            User userOld = userService.getById(id).get();
            playlists = userOld.getPlaylists();
            user.setPlaylists(playlists);
        }

        JSONObject topSong = playerService.getTopSong(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        ArrayList<LinkedHashMap> topSongList = (ArrayList<LinkedHashMap>) topSong.get("items");
        LinkedHashMap topTrack = topSongList.get(0);
        String trackId = (String) topTrack.get("id");
        Song song = new Song(trackId);

        user.setTopSong(songService.save(song));
        user.setId(id);
        user.setDisplayName(displayName);
        user.setImageUrl(imageUrl);
        user.setSpotifyUrl(spotifyUrl);
        user.setArtists(savedArtists);
        user.setAlbums(savedAlbums);
        this.userService.save(user);

        user = this.userService.getById(id).get();

        System.out.println(user.toString());
        session.setAttribute("user", user);
        session.setAttribute("token", spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken());
        System.out.println("this is token" + session.getAttribute("token"));

        session.setAttribute("playlists", user.getPlaylists());
        session.setAttribute("albums", user.getAlbums());

        return "redirect:/profile";
        //System.out.println("Finished");
        //result.put("goToRecentlyPlayedLink", "http://localhost:8080/index/recentlyPlayed");
        //return "redirect:/profile";
        //return "<h1>AccessGranted</h1> <br/>  state:" + state + " <br/>  code:" + code + "<br/> <br/> getToken <a href=\"http://localhost:8080/index/token\">Get Token to interact on user's behalf</a>\n "; // TODO hide code display
    }

    @GetMapping("/logout")
    public String getLogoutPage(HttpServletRequest req){
        req.getSession().invalidate();
        return "redirect:/";
    }
    @GetMapping("/profile")
    public String getProfilePage(){
        return "profile";
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

        currentUser.appendPlaylist(userPlaylist);
        userService.save(currentUser);

        session.setAttribute("user", currentUser);
        return "redirect:/profile";
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
    @DeleteMapping("/deletePlaylist/{id}")
    public String DeletePlaylist(@PathVariable String id, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Playlist> playlists = user.getPlaylists();
        playlists.removeIf(playlist -> playlist.getId().equals(id));
        user.setPlaylists(playlists);
        this.userService.save(user);

        session.setAttribute("user", user);

        playerService.deletePlaylist(spotifyUserAuthorizationCode.getTokenType() + " " + spotifyUserAuthorizationCode.getAccessToken(), id );

        return "redirect:/profile";
    }
}
