package mk.ukim.finki.spotifyandchill.repository;

import mk.ukim.finki.spotifyandchill.model.*;
import org.springframework.data.jpa.repository.*;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
