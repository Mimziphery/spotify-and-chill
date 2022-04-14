package mk.ukim.finki.spotifyandchill.service;


import mk.ukim.finki.spotifyandchill.model.*;
import org.springframework.stereotype.*;

import java.util.*;


public interface UserService {
    List<User> listAll();
    //List<User> topArtist(Artist artist);
    void save(User user);
    Optional<User> getById(String id);
    List<Artist> getTopArtists(String id);
}
