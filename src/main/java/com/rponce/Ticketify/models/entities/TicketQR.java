package com.rponce.Ticketify.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ticket_qr")
public class TicketQR {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@Column(name = "qr")
	private String qr;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "exchange_Date")
	private Date exchangeDate;
	
	@Column(name = "status")
	private Boolean status;
	
	@JoinColumn(name = "id_ticket")
	@ManyToOne(fetch = FetchType.EAGER)
	private Ticket ticket;

	public TicketQR(String qr, Date creationDate, Date exchangeDate, Boolean status, Ticket ticket) {
		super();
		this.qr = qr;
		this.creationDate = creationDate;
		this.exchangeDate = exchangeDate;
		this.status = status;
		this.ticket = ticket;
	}
	
}
