package mk.ukim.finki.spotifyandchill.service;


import mk.ukim.finki.spotifyandchill.model.*;
import org.springframework.stereotype.*;

import java.util.*;


public interface UserService {
    List<User> listAll();
    void save(User user);
    Optional<User> getById(String id);
    Optional<User> findByUsername(String username);
}
