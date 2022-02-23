package mk.ukim.finki.spotifyandchill.model;

import lombok.Data;

@Data
public class SpotifyUserAuthorizationCode {
    private String username;
    private String code;
    private String accessToken;
    private String tokenType;
    private String refreshToken;
}
