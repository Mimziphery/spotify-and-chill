package mk.ukim.finki.spotifyandchill.service;


import mk.ukim.finki.spotifyandchill.model.*;

public interface PlaylistService {
    Playlist save(Playlist playlist);
    Playlist getById(String id);

}
