package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthUserDTO {
	
	@NotEmpty
	private String email;
	
	private String password;
	
}
