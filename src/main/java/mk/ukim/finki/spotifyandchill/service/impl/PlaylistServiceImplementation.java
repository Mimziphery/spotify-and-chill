package mk.ukim.finki.spotifyandchill.service.impl;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.repository.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PlaylistServiceImplementation implements PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImplementation(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist save(Playlist playlist) {
        return Optional.of(playlistRepository.save(playlist)).orElseThrow();
    }

    @Override
    public Playlist getById(String id) {
        return Optional.of(playlistRepository.getById(id)).orElseThrow();
    }

    @Override
    public void deleteById(String id) {
        this.playlistRepository.deleteById(id);
    }


}
