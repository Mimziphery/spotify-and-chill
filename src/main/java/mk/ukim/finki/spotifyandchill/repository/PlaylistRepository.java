package mk.ukim.finki.spotifyandchill.repository;

import mk.ukim.finki.spotifyandchill.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}
