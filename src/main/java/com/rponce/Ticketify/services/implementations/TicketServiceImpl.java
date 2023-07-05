package com.rponce.Ticketify.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.repositories.TicketRepository;
import com.rponce.Ticketify.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public List<Ticket> getAllTickets() {
		
		return ticketRepository.findAll();
	}

	@Override
	public Ticket getTicketByID(UUID uuid) {
	
		return ticketRepository.findFirstTicketByUuid(uuid);
	
	}

	@Override
	public List<Ticket> getTicketsByUser(User user) {
		
		return ticketRepository.findAllByUser(user, Sort.by("createDate").descending());
	}

	@Override
	public void SaveTicket(User user, Tier tier, Date createDate, Order order, Boolean state) throws Exception {
		Ticket newTicket = new Ticket();
		
		newTicket.setUser(user);
		newTicket.setTier(tier);
		newTicket.setCreateDate(createDate);
		newTicket.setOrder(order);
		newTicket.setState(true);
		
		ticketRepository.save(newTicket);
		
	}

	@Override
	public List<Ticket> getTicketsByTier(Tier tier) {
		
		return ticketRepository.findAllByTier(tier);
	}

	@Override
	public List<Ticket> getTicketsByEvent(String eventID) {
		
		UUID uuid = UUID.fromString(eventID);
		
		List<Ticket> tickets = ticketRepository.findAll();
		tickets.stream()
				.filter(t-> t.getTier().getEvent().getId()== uuid)
				.findAny()
				.orElse(null);
		
		return tickets;
	}

	@Override
	public List<Ticket> getTicketsByTierAndUser(Tier tier, User user) {
		
		return ticketRepository.findAllByTierAndUser(tier, user);
	}

	@Override
	public List<Ticket> getTicketsByOrder(Order order) {
		
		return ticketRepository.findAllByOrder(order);
	}

	@Override
	public void ActivateTicket(List<Ticket> tickets) throws Exception {
		
		tickets.forEach(t-> {
			t.setState(true);
			ticketRepository.save(t);
		});
		
	}

	@Override
	public void UpdateTicketOwner(Ticket ticket, User newUser, Date ChangeDate) {
		
		ticket.setUser(newUser);
		ticket.setChangeDate(ChangeDate);
		ticketRepository.save(ticket);
		
	}

	@Override
	public void DeleteTicket(Ticket ticket) {
		
		ticketRepository.delete(ticket);
		
	}

}
