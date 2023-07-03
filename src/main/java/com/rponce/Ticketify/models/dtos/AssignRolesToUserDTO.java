package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AssignRolesToUserDTO {
	
	@NotEmpty
	private String userCode;
	
	@NotEmpty
	private String roleName;
	
}
