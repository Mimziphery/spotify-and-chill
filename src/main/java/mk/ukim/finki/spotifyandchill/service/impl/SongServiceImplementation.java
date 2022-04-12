package mk.ukim.finki.spotifyandchill.service.impl;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.repository.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SongServiceImplementation implements SongService {
    private final SongRepository songRepository;

    public SongServiceImplementation(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song save(Song song) {
        return Optional.of(songRepository.save(song)).orElseThrow();
    }
}
