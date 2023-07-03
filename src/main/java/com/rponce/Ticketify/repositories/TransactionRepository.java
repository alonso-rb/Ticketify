package com.rponce.Ticketify.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Transaction;

public interface TransactionRepository extends ListCrudRepository<Transaction, UUID> {
	
	Transaction findOneByUuid(UUID uuid);
	Transaction findOneByHashEmail(String hashEmail);

}
