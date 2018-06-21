package com.shahan.events.repositories;

import org.springframework.data.repository.CrudRepository;

import com.shahan.events.models.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);

}
