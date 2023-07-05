package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.models.entities.User;

public interface TicketRepository extends ListCrudRepository<Ticket, UUID>{
	
	Ticket findFirstTicketByUuid (UUID uuid);
	List<Ticket> findAllByUser (User user);
	List<Ticket> findAllByTier (Tier tier);
	List<Ticket> findAllByTierAndUser (Tier tier, User user);
	List<Ticket> findAllByOrder(Order order);
}
