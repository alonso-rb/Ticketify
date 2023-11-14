package com.rponce.Ticketify.models.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "public")
@ToString(exclude = { "ticket" })
public class Order {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;

	@JoinColumn(name = "id_user", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@Column(name = "purchase_date")
	private Date purchaseDate;

	@Column(name = "total")
	private Float total;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> ticket;

	public Order(User user, Date purchaseDate, Float total) {
		super();
		this.user = user;
		this.purchaseDate = purchaseDate;
		this.total = total;
	}

}