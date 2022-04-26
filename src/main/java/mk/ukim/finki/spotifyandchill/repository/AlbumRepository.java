package mk.ukim.finki.spotifyandchill.repository;

import mk.ukim.finki.spotifyandchill.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
}
