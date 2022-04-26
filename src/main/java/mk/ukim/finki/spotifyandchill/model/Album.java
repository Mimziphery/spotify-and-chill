package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

@Data
@Getter
@Entity
public class Album {
    @Id
    private String id;

    private String name;

    private String aristName;

    private String imageUrl;
    private String spotifyUrl;

    public Album() {
    }

    public Album(String id, String name, String aristName, String imageUrl, String spotifyUrl) {
        this.id = id;
        this.name = name;
        this.aristName = aristName;
        this.imageUrl = imageUrl;
        this.spotifyUrl = spotifyUrl;
    }
}
