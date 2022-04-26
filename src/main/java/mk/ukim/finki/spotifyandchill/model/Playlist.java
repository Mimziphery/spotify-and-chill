package mk.ukim.finki.spotifyandchill.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Table(name="playlists")
public class Playlist {
    @Id
    private String id;


    public Playlist(String id) {
        this.id = id;
    }

    public Playlist() {

    }
}
