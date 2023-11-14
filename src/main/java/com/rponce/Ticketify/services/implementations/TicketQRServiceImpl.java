package com.rponce.Ticketify.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.TicketQRInfoToSaveDTO;
import com.rponce.Ticketify.models.entities.Ticket;
import com.rponce.Ticketify.models.entities.TicketQR;
import com.rponce.Ticketify.repositories.TicketQRRepository;
import com.rponce.Ticketify.repositories.TicketRepository;
import com.rponce.Ticketify.services.TicketQRService;

import jakarta.transaction.Transactional;

@Service
public class TicketQRServiceImpl implements TicketQRService{

	@Autowired
	private TicketQRRepository ticketQRRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void SaveTicketQR(TicketQRInfoToSaveDTO info, Ticket ticketId) throws Exception {
		
		TicketQR ticketqr = new TicketQR();
		ticketqr.setCreationDate(info.getCreationDate());
		ticketqr.setExchangeDate(info.getExchangeDate());
		ticketqr.setQr(info.getQr());
		ticketqr.setStatus(info.getActive());
		ticketqr.setTicket(ticketId);
		
		ticketQRRepository.save(ticketqr);
	}

	@Override
	public List<TicketQR> GetTicketQRByTicketId(UUID TicketId) {
	
		Ticket ticket = ticketRepository.findFirstTicketByUuid(TicketId);
		return ticketQRRepository.findAllByTicket(ticket);
	}

	@Override
	public TicketQR getTicketQRByQR(String qr) {
		
		return ticketQRRepository.findOneByQr(qr);
	}

}
