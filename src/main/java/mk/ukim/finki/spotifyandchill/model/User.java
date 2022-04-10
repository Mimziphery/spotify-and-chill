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
    private String bio;
    private String gender;
    private String email;
    private Date birthDate;
    private String country;
    private String imageUrl;
    private String spotifyUrl;

    @ManyToMany
    private List<Artist> artists;
//    @ManyToOne
//    private Artist topArtist;

    @Id
    private String id;

    public User(String id, String displayName, String country, String imageUrl, String spotifyUrl, List<Artist> artists) {
        this.id = id;
        this.displayName = displayName;
        this.country = country;
        this.imageUrl = imageUrl;
        this.spotifyUrl = spotifyUrl;
        this.artists = artists;
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

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
    public List<Artist> getArtists(){
        return this.artists;
    }

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
