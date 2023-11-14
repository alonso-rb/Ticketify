package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveInvolvedDTO {

	@NotEmpty
	private String id;

	@NotEmpty
	private String involved;

	@NotEmpty
	private String event;
}