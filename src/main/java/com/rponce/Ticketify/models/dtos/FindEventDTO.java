package com.rponce.Ticketify.models.dtos;

import java.util.List;

import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Tier;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindEventDTO {
	
	@NotEmpty
	private Event event;

	@NotEmpty
	private List<Tier> tiers;
}