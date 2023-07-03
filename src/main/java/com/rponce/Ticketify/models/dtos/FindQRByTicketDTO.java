package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FindQRByTicketDTO {
	
	@NotEmpty
	private String qr;
	
	@NotEmpty
	private Date creationDate;
	
	@NotEmpty
	private Boolean active; 

	@NotEmpty
	private String ticketid;

}
