package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteTransactionDTO {
	
	@NotEmpty
	private String HashEmail;
	
	@NotEmpty
	private String UserToTransferEmail;

}
