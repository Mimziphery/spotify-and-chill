package mk.ukim.finki.spotifyandchill.service.impl;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.repository.*;
import mk.ukim.finki.spotifyandchill.service.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

//    @Override
//    public List<User> topArtist(Artist artist) {
//        return this.userRepository.findAllByTopArtist(artist);
//    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }
}
    