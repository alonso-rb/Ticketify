package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveOrderDTO {
	
	@NotEmpty
	private String usercode;
	
	@NotEmpty
	private Date purchaseDate;
	
	@NotEmpty
	private Double total;

}
