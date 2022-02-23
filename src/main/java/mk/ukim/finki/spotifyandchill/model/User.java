package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {
    private String username;
    private String password;
    private String bio;
    private String gender;
    private String name;
    private String surname;
    private String email;
    private Date birthDate;
    private String profilePicture;

    @ManyToOne
    private Artist topArtist;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private List<User> matches;
//    private List<Artist> likedArtist;
//
//    private List<Song> likedSongs;
//    private Long id;

}
