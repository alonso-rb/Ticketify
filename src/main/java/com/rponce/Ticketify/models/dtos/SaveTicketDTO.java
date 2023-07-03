package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveTicketDTO {
	
	@NotEmpty
	private String tierId;
	
	private Date createDate;
	
	private Date changeDate;
	
	private String orderId; 
	
	private Boolean state;

}
