package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionInfoDTO {

	String transactionID;
	
	String UserFromName;
	
	String UserToName;

	String TicketId;
	
	Date ChangeDate;
	
}
