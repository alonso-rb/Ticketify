package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveTransactionDTO {
	
	@NotEmpty
	private String userCodeFrom;
	
	@NotEmpty
	private String userCodeTo;
	
	private String hashEmail;
	
	private Boolean accepted;
	
	@NotEmpty
	private String ticketCode;

}
