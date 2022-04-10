package mk.ukim.finki.spotifyandchill.service.impl;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.repository.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ArtistServiceImplementation implements ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistServiceImplementation(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }


    @Override
    public Artist save(Artist artist) {
        return Optional.of(this.artistRepository.save(artist)).orElseThrow();
    }
}
