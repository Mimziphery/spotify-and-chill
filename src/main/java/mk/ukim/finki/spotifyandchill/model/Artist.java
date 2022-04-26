package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Getter
@Table(name="artists")
public class Artist {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private String spotifyUrl;

    public Artist(String id,String name, String imageUrl, String spotifyUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.spotifyUrl = spotifyUrl;
    }

    public Artist() {

    }

}
