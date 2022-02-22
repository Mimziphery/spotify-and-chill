package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Date birthDate;
    private Artist topArtist;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBdate() {
        return birthDate;
    }

    public void setBdate(Date bdate) {
        this.birthDate = bdate;
    }

    public List<User> getMatches() {
        return matches;
    }

    public void setMatches(List<User> matches) {
        this.matches = matches;
    }

    public List<Artist> getLikedArtist() {
        return likedArtist;
    }

    public void setLikedArtist(List<Artist> likedArtist) {
        this.likedArtist = likedArtist;
    }

    public List<Song> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(List<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    private List<User> matches;
    private List<Artist> likedArtist;
    private List<Song> likedSongs;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
