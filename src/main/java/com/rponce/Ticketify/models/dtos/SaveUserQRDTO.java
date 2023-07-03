package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class SaveUserQRDTO {
	
	private String qr;
	
	private Date creationDate;
	
	private Boolean active;
}
