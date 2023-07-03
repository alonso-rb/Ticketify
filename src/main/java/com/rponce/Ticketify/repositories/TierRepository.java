package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Tier;

public interface TierRepository extends ListCrudRepository<Tier, UUID>{
	public List<Tier> findAllByEvent(Event event);
	public Tier findByTier(String tier);
}