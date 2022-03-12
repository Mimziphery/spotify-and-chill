//package mk.ukim.finki.spotifyandchill.model;
//
//import lombok.*;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//public class Song {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    @ManyToOne
//    private Artist artist;
//    @ManyToOne
//    private Album album;
//
//    public Song(String name, Artist artist, Album album) {
//        this.name = name;
//        this.artist = artist;
//        this.album = album;
//    }
//
//    public Song() {
//
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Artist getArtist() {
//        return artist;
//    }
//
//    public void setArtist(Artist artist) {
//        this.artist = artist;
//    }
//
//    public Album getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Album album) {
//        this.album = album;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Id
//    public Long getId() {
//        return id;
//    }
//}
