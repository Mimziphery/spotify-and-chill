package mk.ukim.finki.spotifyandchill.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Album {
    private Artist byArtist;
    private Date relaseDate;
    private Genre genre;
    private int number_songs;
    private int duration;
}
