package com.rponce.Ticketify.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
@ToString(exclude = {"userxrole"})
public class Role {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "role")
	private String role;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserXRole> userxrole;

	public Role(String id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	
	
}

