package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdatePasswordDTO {
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String newPassword;
}
