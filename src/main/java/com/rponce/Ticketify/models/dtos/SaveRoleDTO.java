package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveRoleDTO {
	
	@NotEmpty
	private String roleId;
	
	@NotEmpty
	private String roleName;
}
