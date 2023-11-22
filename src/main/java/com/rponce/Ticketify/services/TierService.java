package com.rponce.Ticketify.services;

import java.util.List;

import com.rponce.Ticketify.models.dtos.SaveTierDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Tier;

public interface TierService {
	void saveTier(SaveTierDTO info, Event event) throws Exception;
	Tier findOneById(String id);
	void updateTier(Tier tier, int cantidad);
	List<Tier> findAllTier();
	void deleteTier(String id) throws Exception;
	List<Tier> findByEvent(Event event);
	Tier findByTier(String tier);
}