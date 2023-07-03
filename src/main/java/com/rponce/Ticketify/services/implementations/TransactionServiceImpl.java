package com.rponce.Ticketify.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.Transaction;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.repositories.TransactionRepository;
import com.rponce.Ticketify.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRep;

	@Override
	public void SaveNewTransaction(User userTo, User userFrom, String hashEmail, Ticket ticket, Boolean accepted)
			throws Exception {
		Transaction newTransaction = new Transaction();
		
		newTransaction.setUserTo(userTo);
		newTransaction.setUserFrom(userFrom);
		newTransaction.setHashEmail(hashEmail);
		newTransaction.setTicket(ticket);
		newTransaction.setAccepted(accepted);
		
		transactionRep.save(newTransaction);
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRep.findAll();
	}

	@Override
	public Transaction findTransactionById(String id) {
		
		UUID uuid = UUID.fromString(id);
		
		return transactionRep.findOneByUuid(uuid);
	}

	@Override
	public Transaction findTransactionByHashEmail(String hashCode) {
		
		return transactionRep.findOneByHashEmail(hashCode);
	}

	@Override
	public void CompleteTransaction(Transaction transaction) {
		
		transaction.setAccepted(true);
		transactionRep.save(transaction);
		
	}

}
