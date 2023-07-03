package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.TicketQR;

public interface TicketQRRepository extends ListCrudRepository<TicketQR, UUID>{
	
	List<TicketQR> findAllByTicket(Ticket ticket);
	TicketQR findOneByQr(String qr);
}
