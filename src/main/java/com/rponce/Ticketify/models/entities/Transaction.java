package com.rponce.Ticketify.models.entities;

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
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@JoinColumn(name = "id_user_to", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User userTo;
	
	@JoinColumn(name = "id_user_from", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User userFrom;
	
	@Column(name = "hash_email")
	private String hashEmail;
	
	@Column(name = "accepted")
	private Boolean accepted;
	
	@JoinColumn(name = "id_ticket", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private Ticket ticket;

	public Transaction(User userTo, User userFrom, String hashEmail, Boolean accepted, Ticket ticket) {
		super();
		this.userTo = userTo;
		this.userFrom = userFrom;
		this.hashEmail = hashEmail;
		this.accepted = accepted;
		this.ticket = ticket;
	}
	
	
}
