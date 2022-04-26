package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.*;
import java.util.*;

@Data
@Entity
@Table(name="users")
@Getter
public class User {
    private String displayName;
    private String imageUrl;
    private String spotifyUrl;
    private String country;

    @ManyToMany
    private List<Artist> artists;

    @ManyToMany
    private List<Album> albums;

    @OneToMany
    private List<Playlist> playlists;

    @ManyToOne
    private Song topSong;

    @Id
    private String id;


    public User() {
        this.playlists = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artists = new ArrayList<>();
    }
    public void appendPlaylist(Playlist playlist){
        this.playlists.add(playlist);
    }

}
