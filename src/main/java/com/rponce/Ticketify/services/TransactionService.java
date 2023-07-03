package com.rponce.Ticketify.services;

import java.util.List;

import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.Transaction;
import com.rponce.Ticketify.models.entities.User;

public interface TransactionService {
	
	public void SaveNewTransaction(User userTo, User userFrom, String hashEmail,  Ticket ticket, Boolean accepted) throws Exception;
	List<Transaction> getAllTransactions();
	Transaction findTransactionById(String id);
	Transaction findTransactionByHashEmail(String hashCode);
	public void CompleteTransaction(Transaction transaction);
	
}
