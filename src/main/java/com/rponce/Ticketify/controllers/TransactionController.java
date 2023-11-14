package com.rponce.Ticketify.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.CompleteTransactionDTO;
import com.rponce.Ticketify.models.dtos.SaveTransactionDTO;
import com.rponce.Ticketify.models.dtos.TransactionInfoDTO;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.Transaction;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.services.TicketService;
import com.rponce.Ticketify.services.TransactionService;
import com.rponce.Ticketify.services.UserService;
import com.rponce.Ticketify.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
@CrossOrigin("*")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private Date dateTransaction;
	
	@GetMapping("/all")
	private ResponseEntity<?> GetAllTransactions(){
		List<Transaction> transactions = transactionService.getAllTransactions();
		
		if(transactions == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
	
	@PostMapping("/request")
	private ResponseEntity<?> RequestNewTransaction(@ModelAttribute @Valid SaveTransactionDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		}
		
		//Information needed for requesting a transaction
		UUID uuid = UUID.fromString(info.getTicketCode());
		User userFrom = userService.FindOneUserById(info.getUserCodeFrom());
		User userTo = userService.FindOneUserById(info.getUserCodeTo());
		String userEmail = userFrom.getEmail();
		Ticket ticket = ticketService.getTicketByID(uuid);
		Integer hash = new Date().hashCode();
		String hashEmail = hash.toString();
		String text = ". Ingresa el siguiente código para completar la transferencia del ticket ";
		
		//Sending email to confirm transaction
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ticketifysv@gmail.com");
		message.setTo(userEmail);
		message.setSubject("Código de confirmación para transacción");
		message.setText("Saludos, ".concat(userTo.getFirstname()).concat(text).concat(hashEmail));
		mailSender.send(message);
		
		Date now = new Date();
		this.dateTransaction = now;		
		//Poblating data
		info.setHashEmail(hashEmail);
		info.setAccepted(false);


		if(userFrom == null || userTo == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			transactionService.SaveNewTransaction(userTo, userFrom, info.getHashEmail(), ticket, null);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PostMapping("/complete")
	private ResponseEntity<?> CompleteTransaction(@ModelAttribute @Valid CompleteTransactionDTO info, BindingResult validations){
		
		//Getting the data
		Transaction transaction = transactionService.findTransactionByHashEmail(info.getHashEmail());
		User user = userService.FindOneUserByEmail(info.getUserToTransferEmail());
		Ticket ticket = transaction.getTicket();
		
		Date dateNow = new Date();
		long date = dateTransaction.getTime();
		Date deadlineDate = new Date(date + (10 * 60 * 1000));
		
		
		if(transaction == null || user == null || ticket == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(dateNow.after(deadlineDate)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			ticketService.UpdateTicketOwner(ticket, user, dateNow);
			transactionService.CompleteTransaction(transaction);
			
			TransactionInfoDTO response = new TransactionInfoDTO();
			response.setTransactionID(transaction.getUuid().toString());
			response.setUserFromName(transaction.getUserFrom().getUsername());
			response.setUserToName(transaction.getUserTo().getUsername());
			response.setTicketId(ticket.getUuid().toString());
			response.setChangeDate(ticket.getChangeDate());
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
}
