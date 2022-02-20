package mk.ukim.finki.spotifyandchill.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date releaseDate;
    private Genre genre;
    private int numberSongs;
    private int duration;

    @ManyToOne
    private Artist artist;
    @OneToMany
    private List<Song> songs;

    public Album(Artist artist, Date releaseDate, Genre genre, List<Song> songs, int numberSongs, int duration) {
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.songs = songs;
        this.numberSongs = numberSongs;
        this.duration = duration;
    }

    public Album() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int getNumberSongs() {
        return numberSongs;
    }

    public void setNumberSongs(int numberSongs) {
        this.numberSongs = numberSongs;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
