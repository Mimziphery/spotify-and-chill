package mk.ukim.finki.spotifyandchill.services;


import mk.ukim.finki.spotifyandchill.model.*;

import java.util.*;

public interface UserService {
    List<User> listAll();
    List<User> topArtist(Artist artist);
}
