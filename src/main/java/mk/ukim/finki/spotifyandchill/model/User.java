package mk.ukim.finki.spotifyandchill.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Date bdate;

    private List<User> matchesList;
    private List<Artist> likedArtist;
    private List<Song> faveSongs;

}
