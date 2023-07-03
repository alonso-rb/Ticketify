package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Token;
import com.rponce.Ticketify.models.entities.User;

public interface TokenRepository extends ListCrudRepository<Token, UUID>{
	
	List<Token> findByUserAndActive(User user, Boolean active);

}
