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
@Table(name = "event")
@ToString(exclude = { "involved", "sponsors" })
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;

	@Column(name = "title")
	private String title;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "image")
	private String image;

	@Column(name = "date")
	private Date date;

	@Column(name = "hour")
	private Date hour;

	@JoinColumn(name = "id_category", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;

	@Column(name = "place")
	private String place;

	@Column(name = "address")
	private String address;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Involved> involved;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Sponsor> sponsors;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Tier> tiers;

	public Event(String title, Boolean status, String image, Date date, Date hour, Category category, String place,
			String address) {
		super();
		this.title = title;
		this.status = status;
		this.image = image;
		this.date = date;
		this.hour = hour;
		this.category = category;
		this.place = place;
		this.address = address;
	}

}
