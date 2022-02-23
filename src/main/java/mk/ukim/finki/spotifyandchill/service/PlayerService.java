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
import static org.springframework.http.HttpMethod.GET;
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
}