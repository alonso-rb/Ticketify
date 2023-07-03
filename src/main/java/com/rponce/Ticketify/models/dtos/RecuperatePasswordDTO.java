package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RecuperatePasswordDTO {

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String newPassword;
	
	@NotEmpty
	private String Code;

}
