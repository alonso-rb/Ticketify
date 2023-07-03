package com.rponce.Ticketify.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowOrderDTO {
	
	private String id;
	
	private String eventName;
	
	private String tier;
	
	private Integer TotalTickets;
	
	private Float subtotal;
	
	private Float taxes;
	
	private Float total;

}
