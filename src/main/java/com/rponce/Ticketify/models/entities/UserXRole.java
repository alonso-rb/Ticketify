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
@Table(name = "userxrole")
public class UserXRole {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@JoinColumn(name = "id_user", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@JoinColumn(name = "id_role", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;
	
	@Column(name = "assignation_date")
	private Date assignationDate;
	
	@Column(name = "status")
	private Boolean status;

	public UserXRole(User user, Role role, Date assignationDate, Boolean status) {
		super();
		this.user = user;
		this.role = role;
		this.assignationDate = assignationDate;
		this.status = status;
	}
}