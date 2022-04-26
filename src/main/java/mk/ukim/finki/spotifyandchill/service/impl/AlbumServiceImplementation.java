package mk.ukim.finki.spotifyandchill.service.impl;

import mk.ukim.finki.spotifyandchill.model.Album;
import mk.ukim.finki.spotifyandchill.repository.AlbumRepository;
import mk.ukim.finki.spotifyandchill.service.AlbumService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlbumServiceImplementation implements AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumServiceImplementation(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album save(Album album) {
        return Optional.of(this.albumRepository.save(album)).orElseThrow();
    }
}
