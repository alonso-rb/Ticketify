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
@Table(name="user_qr")
public class UserQR {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@Column(name = "qr", unique = true)
	private String qr;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "status")
	private Boolean status;
	
	@JoinColumn(name = "id_user", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User userID;

	public UserQR(String qr, Date creationDate, Boolean status, User userID) {
		super();
		this.qr = qr;
		this.creationDate = creationDate;
		this.status = status;
		this.userID = userID;
	}
	
	
}