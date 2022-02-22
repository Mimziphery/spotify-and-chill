package mk.ukim.finki.spotifyandchill.services.impl;

import mk.ukim.finki.spotifyandchill.model.*;
import mk.ukim.finki.spotifyandchill.repository.*;
import mk.ukim.finki.spotifyandchill.services.*;

import java.util.*;

public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> topArtist(Artist artist) {
        return this.userRepository.findAllByTopArtist(artist);
    }
}
