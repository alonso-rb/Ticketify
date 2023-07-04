package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketQRInfoDTO {

	private String qr;

	private Date creationDate;

	private String eventName;

	private String eventPlace;

	private Date Time;

	private String tierName;
	
}
