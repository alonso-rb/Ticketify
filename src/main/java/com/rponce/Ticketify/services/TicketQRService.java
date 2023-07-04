package com.rponce.Ticketify.services;

import java.util.List;
import java.util.UUID;

import com.rponce.Ticketify.models.dtos.SaveTicketQRDTO;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.TicketQR;


public interface TicketQRService {
	public void SaveTicketQR(SaveTicketQRDTO info, Ticket ticketId) throws Exception;
	List<TicketQR> GetTicketQRByTicketId(UUID TicketId);
	TicketQR getTicketQRByQR(String qr);
}
