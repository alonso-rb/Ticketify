package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTierDTO {
	
	@NotEmpty(message = "The nameTier is required")
	private String name;

	@NotNull(message = "The capacity is required")
	private int capacity;

	@NotNull(message = "The price is required")
	private float price;

}
