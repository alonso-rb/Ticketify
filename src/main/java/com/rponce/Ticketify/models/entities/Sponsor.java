package com.rponce.Ticketify.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sponsor")
public class Sponsor {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "sponsor")
	private String sponsor;

	@JoinColumn(name = "id_event")
	@ManyToOne(fetch = FetchType.EAGER)
	private Event event;

	public Sponsor(String id, String sponsor, Event event) {
		super();
		this.id = id;
		this.sponsor = sponsor;
		this.event = event;
	}
	
	
}

