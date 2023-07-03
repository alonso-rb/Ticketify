package com.rponce.Ticketify.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public")
@ToString(exclude = {"userQR","order","userxrole", "ticket", "transactionsTo", "transactionsFrom"})
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1460435087476558985L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;
	
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@Column(name = "active")
	private Boolean active;
	
	@OneToMany(mappedBy = "userID", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserQR> userQR;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Order> order;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserXRole> userxrole;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> ticket;
	
	@OneToMany(mappedBy = "userTo", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> transactionsTo;
	
	@OneToMany(mappedBy = "userFrom", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> transactionsFrom;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Token> tokens;

	public User(String email, String firstname, String lastname, Boolean active) {
		super();
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.active = active;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.active;
	}
	
	
	

}
