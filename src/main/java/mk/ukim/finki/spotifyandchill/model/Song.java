package mk.ukim.finki.spotifyandchill.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Table(name="songs")
public class Song {
    @Id
    private String id;

    public Song(String id) {
        this.id = id;
    }

    public Song() {

    }

}