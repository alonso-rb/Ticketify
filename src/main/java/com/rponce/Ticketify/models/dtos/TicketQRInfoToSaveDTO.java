package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketQRInfoToSaveDTO {
	
	private String qr;
	
	private Date creationDate;
	
	private Date exchangeDate;
	
	private Boolean active;

}
