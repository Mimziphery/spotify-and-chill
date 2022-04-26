package mk.ukim.finki.spotifyandchill.service;


import mk.ukim.finki.spotifyandchill.model.*;

import java.util.Optional;

public interface PlaylistService {
    Playlist save(Playlist playlist);
    Playlist getById(String id);
    void deleteById(String id);
}
