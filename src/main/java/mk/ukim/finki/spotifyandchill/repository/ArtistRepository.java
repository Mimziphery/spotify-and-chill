package mk.ukim.finki.spotifyandchill.repository;

import mk.ukim.finki.spotifyandchill.model.*;
import org.springframework.data.jpa.repository.*;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}