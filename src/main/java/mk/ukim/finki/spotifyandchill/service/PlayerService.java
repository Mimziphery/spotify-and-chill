package mk.ukim.finki.spotifyandchill.service;

import lombok.extern.slf4j.*;
import mk.ukim.finki.spotifyandchill.config.*;
import mk.ukim.finki.spotifyandchill.model.*;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

import static mk.ukim.finki.spotifyandchill.service.AuthorizationService.callAction;
import static mk.ukim.finki.spotifyandchill.service.utlis.RestCallUtilis.checkResponseCodeExpected;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Component
@Slf4j
public class PlayerService {
    @Autowired
    AuthorizationService authorizationService;

    SpotifyUserAuthorizationCode spotifyUserAuthorizationCode = new SpotifyUserAuthorizationCode();

    String authCode;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SpotifyConnectionConfig spotifyConnectionConfig;


    /**
     * Get Current User's Recently Played Tracks
     *
     * @return
     */
    public JSONObject getCurrentUser(String token) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getCurrentUser();

        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "getCurrentUser", createIndexUrl, GET,
                new HttpEntity<>(null, requestHeaders), JSONObject.class, null);

        // check response
        checkResponseCodeExpected(responseEntity, Arrays.asList(NO_CONTENT, OK), "getCurrentUser");

        return responseEntity.getBody();
    }

    public JSONObject getRecentlyPlayed(String token) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getPlayerUrlRecentlyPlayed();

        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "getRecentlyPlayed", createIndexUrl, GET,
                new HttpEntity<>(null, requestHeaders), JSONObject.class, null);

        // check response
        checkResponseCodeExpected(responseEntity, Arrays.asList(NO_CONTENT, OK), "getRecentlyPlayed");

        return responseEntity.getBody();
    }

    public JSONObject getTopArtists(String token) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getTopArtists();

        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "getTopArtists", createIndexUrl, GET,
                new HttpEntity<>(null, requestHeaders), JSONObject.class, null);

        // check response

        return responseEntity.getBody();
    }

    public JSONObject postPlaylist(String token, JSONObject body, String userId) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getCreatePlaylist();

        createIndexUrl = String.format(createIndexUrl, userId);
        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "createPlaylist", createIndexUrl, POST,
                new HttpEntity<>(body, requestHeaders), JSONObject.class, null);

        // check response

        return responseEntity.getBody();
    }


    public JSONObject editPlaylist(String token, JSONObject body, String playlistId) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getEditPlaylist();

        createIndexUrl = String.format(createIndexUrl, playlistId);
        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "editPlaylist", createIndexUrl, PUT,
                new HttpEntity<>(body, requestHeaders), JSONObject.class, null);

        // check response

        return responseEntity.getBody();
    }

    public JSONObject addSongsToPlaylist(String token, JSONObject body, String playlistId) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getAddSongsToPlaylist();

        createIndexUrl = String.format(createIndexUrl, playlistId);
        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "addSongsToPlaylist", createIndexUrl, POST,
                new HttpEntity<>(body, requestHeaders), JSONObject.class, null);

        // check response

        return responseEntity.getBody();
    }

    public JSONObject editSongsOfPlaylist(String token, JSONObject body, String playlistId) {
        // prepare create index url
        String createIndexUrl = spotifyConnectionConfig.getEditSongsOfPlaylist();

        createIndexUrl = String.format(createIndexUrl, playlistId);
        // prepare request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Authorization", token);

        // hit request
        ResponseEntity<JSONObject> responseEntity = callAction(restTemplate, "editSongsOfPlaylist", createIndexUrl, PUT,
                new HttpEntity<>(body, requestHeaders), JSONObject.class, null);

        // check response

        return responseEntity.getBody();
    }
}