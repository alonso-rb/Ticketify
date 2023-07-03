package com.rponce.Ticketify.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.User;

public interface UserRepository extends ListCrudRepository<User, UUID> {
	
	User findOneByUuid(UUID id);
	User findOneByEmail(String email);
	User findOneByEmailAndPassword(String email, String password);

}
