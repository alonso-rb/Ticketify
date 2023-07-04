package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRolesDTO {
	
	@NotEmpty
	private String UserId;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String roleAssigned;
	
	@NotEmpty
	private Date dateAssigned;

	@NotEmpty
	private Boolean status;
}
