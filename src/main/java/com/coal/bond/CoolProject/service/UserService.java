package com.coal.bond.CoolProject.service;

import com.coal.bond.CoolProject.domain.User;
import com.coal.bond.CoolProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean create(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<User> read(Long id) {
        return userRepository.findById(id);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User update(Long id, User user) {
        final Optional<User> fetchedUser = userRepository.findById(id);
        if (fetchedUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        final Optional<User> fetchedUser = userRepository.findById(id);
        if (fetchedUser.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
