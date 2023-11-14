package com.rponce.Ticketify.models.entities;

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
@Table(name = "tier")
@ToString(exclude = { "tickets" })
public class Tier {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@Column(name = "tier")
	private String tier;

	@Column(name = "price")
	private Float price;

	@Column(name = "capacity")
	private int capacity;

	@JoinColumn(name = "id_event")
	@ManyToOne(fetch = FetchType.EAGER)
	private Event event;

	@OneToMany(mappedBy = "tier", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> tickets;

	public Tier(String tier, Float price, int capacity, Event event) {
		super();
		this.tier = tier;
		this.price = price;
		this.capacity = capacity;
		this.event = event;
	}
}
