package com.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.address.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
}
