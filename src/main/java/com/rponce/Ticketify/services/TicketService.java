package com.rponce.Ticketify.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.models.entities.User;

public interface TicketService {
	
	public void SaveTicket(User user, Tier tier, Date createDate, Order order, Boolean state) throws Exception;
	public void ActivateTicket(List<Ticket> tickets) throws Exception;
	public void UpdateTicketOwner(Ticket ticket, User newUser, Date changeDate);
	public void DeleteTicket(Ticket ticket);
	List<Ticket> getAllTickets();
	Ticket getTicketByID(UUID uuid);
	List<Ticket> getTicketsByUser(User user);
	List<Ticket> getTicketsByTier(Tier tier);
	List<Ticket> getTicketsByEvent(String eventID);
	List<Ticket> getTicketsByOrder(Order order);
	List<Ticket> getTicketsByTierAndUser (Tier tier, User user);

}
