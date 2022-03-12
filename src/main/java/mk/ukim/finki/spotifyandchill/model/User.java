package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {
    private String displayName;
    private String bio;
    private String gender;
    private String email;
    private Date birthDate;
    private String country;
    private String imageUrl;
    private String spotifyUrl;

//    @ManyToOne
//    private Artist topArtist;

    @Id
    private String id;

    public User(String id, String displayName, String country, String imageUrl, String spotifyUrl) {
        this.id = id;
        this.displayName = displayName;
        this.country = country;
        this.imageUrl = imageUrl;
        this.spotifyUrl = spotifyUrl;
    }

    public User() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

//    public Artist getTopArtist() {
//        return topArtist;
//    }
//
//    public void setTopArtist(Artist topArtist) {
//        this.topArtist = topArtist;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    //    private List<User> matches;
//    private List<Artist> likedArtist;
//
//    private List<Song> likedSongs;
//    private Long id;

}
