package com.rponce.Ticketify.controllers;

import java.util.ArrayList;
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

import com.rponce.Ticketify.models.dtos.FindQRByTicketDTO;
import com.rponce.Ticketify.models.dtos.SaveTicketQRDTO;
import com.rponce.Ticketify.models.dtos.TicketQRInfoDTO;
import com.rponce.Ticketify.models.dtos.TicketQRInfoToSaveDTO;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.TicketQR;
import com.rponce.Ticketify.services.TicketQRService;
import com.rponce.Ticketify.services.TicketService;
import com.rponce.Ticketify.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticketqr")
@CrossOrigin("*")
public class TicketQRController {
	
	@Autowired
	private TicketQRService ticketqrService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/generate")
	private ResponseEntity<?> SaveTicketQR(@ModelAttribute @Valid SaveTicketQRDTO info,
			BindingResult validations){
		
		Integer qr1 = new Date().hashCode();
		String begginer = "QR-";
		String qrhashed = begginer.concat(qr1.toString());
		
		TicketQRInfoToSaveDTO infoToSave = new TicketQRInfoToSaveDTO();
		
		infoToSave.setQr(qrhashed);
		
		Date date1 = new Date();
		infoToSave.setCreationDate(date1);
		Date date2 = new Date();
		infoToSave.setExchangeDate(date2);
		infoToSave.setActive(true);
		
		UUID uuid = UUID.fromString(info.getTicketId());
		Ticket ticket = ticketService.getTicketByID(uuid);
		
		if(ticket == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()),
			HttpStatus.BAD_REQUEST);
		}
		
		try {
			ticketqrService.SaveTicketQR(infoToSave, ticket);
			TicketQR qrticket = ticketqrService.getTicketQRByQR(infoToSave.getQr());

			TicketQRInfoDTO response = new TicketQRInfoDTO();
			response.setQr(qrticket.getQr());
			response.setCreationDate(qrticket.getCreationDate());
			response.setEventName(qrticket.getTicket().getTier().getEvent().getTitle());
			response.setEventPlace(qrticket.getTicket().getTier().getEvent().getPlace());
			response.setTierName(qrticket.getTicket().getTier().getTier());
			response.setTime(qrticket.getTicket().getTier().getEvent().getHour());

			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/ticket-id/{ticketid}")
	private ResponseEntity<?> GetTicketQRByTicket(@PathVariable (name = "ticketid") String id){
		
		UUID uuid = UUID.fromString(id);
		Ticket ticket = ticketService.getTicketByID(uuid);
		
		if(ticket == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<FindQRByTicketDTO> ticketqrlist = new ArrayList<>();
		List<TicketQR> ticketsQR = ticketqrService.GetTicketQRByTicketId(uuid);
		
		ticketsQR.stream()
		.forEach(t->{
			FindQRByTicketDTO data = new FindQRByTicketDTO();
			data.setCreationDate(t.getCreationDate());
			data.setActive(t.getStatus());
			data.setQr(t.getQr());
			data.setTicketid(t.getTicket().getUuid().toString());
			
			ticketqrlist.add(data);
		});
		
		return new ResponseEntity<>(ticketqrlist, HttpStatus.OK);
	}
	
	@GetMapping("/ticket-qr/{ticketqr}")
	private ResponseEntity<?> GetTicketByTicketQR(@PathVariable(name = "ticketqr") String qr){
		
		TicketQR ticketqr = ticketqrService.getTicketQRByQR(qr);
		Ticket ticket = ticketqr.getTicket();
		
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}
	
}
