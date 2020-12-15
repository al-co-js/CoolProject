package com.coal.bond.CoolProject.repository;

import com.coal.bond.CoolProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
