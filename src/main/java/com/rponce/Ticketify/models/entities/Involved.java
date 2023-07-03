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
@Table(name = "involved")
public class Involved {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "involved")
	private String involved;

	@JoinColumn(name = "id_event")
	@ManyToOne(fetch = FetchType.EAGER)
	private Event event;

	public Involved(String id, String involved, Event event) {
		super();
		this.id = id;
		this.involved = involved;
		this.event = event;
	}

}
