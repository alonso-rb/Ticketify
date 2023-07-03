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
@Table(name = "ticket")
@ToString(exclude = {"transaction", "ticketqr"})
public class Ticket {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@JoinColumn(name = "id_user", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@JoinColumn(name = "id_tier", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private Tier tier;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "change_date", nullable = true)
	private Date changeDate;
	
	@Column(name = "state")
	private Boolean state;

	@JoinColumn(name = "id_order", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private Order order;
	
	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketQR> ticketqr;
	
	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> transaction;
	
	public Ticket(User user, Tier tier, Date createDate, Date changeDate, Boolean state, Order order) {
		super();
		this.user = user;
		this.tier = tier;
		this.createDate = createDate;
		this.changeDate = changeDate;
		this.state = state;
		this.order = order;
	}
}
