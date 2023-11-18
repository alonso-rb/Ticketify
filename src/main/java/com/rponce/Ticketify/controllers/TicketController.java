package com.rponce.Ticketify.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.ExchangeTicketDTO;
import com.rponce.Ticketify.models.dtos.SaveTicketDTO;
import com.rponce.Ticketify.models.dtos.ShowOrderDTO;
import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.TicketQR;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.services.OrderService;
import com.rponce.Ticketify.services.TicketQRService;
import com.rponce.Ticketify.services.TicketService;
import com.rponce.Ticketify.services.TierService;
import com.rponce.Ticketify.services.UserService;
import com.rponce.Ticketify.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
@CrossOrigin("*")
public class TicketController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TierService tierService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private TicketQRService ticketqrService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/save/{numberOfTickets}")
	private ResponseEntity<?> SaveTicket(@ModelAttribute @Valid SaveTicketDTO info, BindingResult validation, 
			@PathVariable(name = "numberOfTickets") int number){
		
		//Registering date
		Date date = new Date();
		
		//Setting data to send
		User userTicket = userService.findUserAuthenticated();
		Tier tierTicket = tierService.findOneById(info.getTierId());
		Float totalPrice = tierTicket.getPrice()*number;
		Float taxes = 10.00f;
		
		if(validation.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validation.getFieldErrors()), 
					HttpStatus.BAD_REQUEST);
		}
		
		if(userTicket == null || tierTicket == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			//Create Order
			orderService.SaveOrder(totalPrice+taxes, userTicket, date);
			Order currOrder = orderService.findCurrentOrder(totalPrice+taxes, userTicket, date);
			
			for(int i = 0; i < number; i++) {
				ticketService.SaveTicket(userTicket, tierTicket, date, currOrder, false);
			}
			
			ShowOrderDTO order = new ShowOrderDTO();
			order.setId(currOrder.getUuid().toString());
			order.setEventName(tierTicket.getEvent().getTitle());
			order.setTier(tierTicket.getTier());
			order.setSubtotal(totalPrice);
			order.setTaxes(taxes);
			order.setTotalTickets(number);
			order.setTotal(totalPrice+taxes);
			
			return new ResponseEntity<>(order, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/exchange")
	private ResponseEntity<?> ExchangeTicket(@ModelAttribute @Valid ExchangeTicketDTO info, BindingResult validation){
		
		Ticket ticket = ticketqrService.getTicketQRByQR(info.getQr()).getTicket();
		
		if(ticket == null) {
			return new ResponseEntity<>("No fue encontrado el Ticket", HttpStatus.BAD_REQUEST);
		}
		
		if(ticket.getState() == false) {
			return new ResponseEntity<>("Ticket ya fue canjeado", HttpStatus.BAD_REQUEST);
		}
		
		try {
			ticketService.ExchangeTicket(ticket);
			Ticket infoToSend = ticketService.getTicketByID(ticket.getUuid());
			return new ResponseEntity<>(infoToSend, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@PostMapping("/finish")
	private ResponseEntity<?> FinishTicketBuy(String OrderId) {
		
		UUID uuid = UUID.fromString(OrderId);
		
		Order order = orderService.findOrderById(uuid);
		List<Ticket> tickets = ticketService.getTicketsByOrder(order);
		
		try {
			ticketService.ActivateTicket(tickets);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PostMapping("/abort")
	private ResponseEntity<?> AbortTicketBuy(String OrderId) {
		
		UUID uuid = UUID.fromString(OrderId);
		Order orderToDelete = orderService.findOrderById(uuid);
		
		try {
			List<Ticket> tickets = ticketService.getTicketsByOrder(orderToDelete);
			for(Ticket t : tickets) {
				ticketService.DeleteTicket(t);
			}
			orderService.DeleteOrder(orderToDelete);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/all")
	private ResponseEntity<?> GetAllTickets() {
		if(ticketService.getAllTickets() == null) {
			return new ResponseEntity<>(
					HttpStatus.NOT_FOUND
					);
		}
		
		return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
	}
	
	@GetMapping("/ticketid/{id}")
	private ResponseEntity<?> GetTicketById(@PathVariable(name = "id") String id){
		
		UUID uuid = UUID.fromString(id);
		
		if(ticketService.getTicketByID(uuid) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ticketService.getTicketByID(uuid), HttpStatus.OK);
	}
	
	@GetMapping("/userid/{userid}")
	private ResponseEntity<?> getTicketsByUser(@PathVariable(name = "userid") String id){
		
		User user = userService.FindOneUserById(id);
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(ticketService.getTicketsByUser(user) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ticketService.getTicketsByUser(user), HttpStatus.OK);
		
	}
	
	@GetMapping("/tierid/{tierid}")
	private ResponseEntity<?> getTicketsByTier(@PathVariable(name = "tierid") String id){
		
		Tier tier = tierService.findOneById(id);
		
		if(tier == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(ticketService.getTicketsByTier(tier) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ticketService.getTicketsByTier(tier), HttpStatus.OK);
		
	}

}
