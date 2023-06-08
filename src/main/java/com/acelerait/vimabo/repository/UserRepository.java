package com.acelerait.vimabo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acelerait.vimabo.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByEmail(String email); //Buscamos en base al email
	
	//User findById(Long id); ->orElse(null)
	
}
