package com.coal.bond.CoolProject.repository;

import com.coal.bond.CoolProject.domain.User;

import java.util.ArrayList;

public interface UserRepository {
    User save(User user);

    User findByName(String name);

    ArrayList<User> findAll();
}
