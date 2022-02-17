package mk.ukim.finki.spotifyandchill.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Artist {
    private String artistname;
    private Genre genre;
    private String country;
    private List<Album> albumList;

}
