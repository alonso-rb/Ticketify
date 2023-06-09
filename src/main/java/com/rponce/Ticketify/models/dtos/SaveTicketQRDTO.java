package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveTicketQRDTO {
	
	@NotEmpty
	private String ticketId;

}
