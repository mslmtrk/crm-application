package com.spring.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
}
