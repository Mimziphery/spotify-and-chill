package mk.ukim.finki.spotifyandchill.repository;

import mk.ukim.finki.spotifyandchill.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //List<User> findAllByTopArtist(Artist artist);
}
