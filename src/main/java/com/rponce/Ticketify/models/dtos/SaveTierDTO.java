package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveTierDTO {

	@NotEmpty
	private String tier;

	// @NotEmpty
	private Float price;

	// @NotEmpty
	private int capacity;

	@NotEmpty
	private String event;
}
