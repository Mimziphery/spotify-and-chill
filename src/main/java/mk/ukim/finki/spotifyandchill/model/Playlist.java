package mk.ukim.finki.spotifyandchill.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name="playlists")
public class Playlist {
    @Id
    private String id;


    public Playlist(String id) {
        this.id = id;
    }

    public Playlist() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
