package mk.ukim.finki.spotifyandchill.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@PropertySource("classpath:application.properties")
public class SpotifyConnectionConfig {

    @Value("96faf0542a934748ab800dae8f474e70")
    private String clientId;

    @Value("1961f39ea9a847c7aaf7729d5b07744c")
    private String clientSecret;

    @Setter(AccessLevel.NONE)
    private String authorizeUrl = "https://accounts.spotify.com/authorize/";

    @Setter(AccessLevel.NONE)
    private String authorizeResponseType = "code";

    @Setter(AccessLevel.NONE)
    // private String authorizationScope = "user-read-private user-read-email streaming app-remote-control playlist-read-collaborative user-modify-playback-state";
    private String authorizationScope = "user-read-private playlist-read-private playlist-modify-public user-library-read user-read-recently-played streaming app-remote-control";

    @Value("${spotify.authorization.redirectURL}")
    private String spotifyAuthorizationRedirectURL;

    @Setter(AccessLevel.NONE)
    private String tokenUrl = "https://accounts.spotify.com/api/token";

    @Setter(AccessLevel.NONE)
    private String playerUrlRecentlyPlayed = "https://api.spotify.com/v1/me/player/recently-played";

    @Setter(AccessLevel.NONE)
    private String currentUser = "https://api.spotify.com/v1/me";

    @Setter(AccessLevel.NONE)
    private String albumUrl = "https://api.spotify.com/v1/albums/";

    @Setter(AccessLevel.NONE)
    private String topArtists = "https://api.spotify.com/v1/me/top/artists?limit=3";

    @Setter(AccessLevel.NONE)
    private String topArtistsLongTerm = "https://api.spotify.com/v1/me/top/artists?limit=3&time_range=long_term";

    @Setter(AccessLevel.NONE)
    private String createPlaylist = "https://api.spotify.com/v1/users/%s/playlists";

    @Setter(AccessLevel.NONE)
    private String editPlaylist = "https://api.spotify.com/v1/playlists/%s";

    @Setter(AccessLevel.NONE)
    private String addSongsToPlaylist = "https://api.spotify.com/v1/playlists/%s/tracks";

    @Setter(AccessLevel.NONE)
    private String editSongsOfPlaylist = "https://api.spotify.com/v1/playlists/%s/tracks";

    @Setter(AccessLevel.NONE)
    private String deletePlaylist = "https://api.spotify.com/v1/playlists/%s/followers";

    @Setter(AccessLevel.NONE)
    private String userAlbums = "https://api.spotify.com/v1/me/albums?limit=3";

    @Setter(AccessLevel.NONE)
    private String userTopSong = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=1";
}
