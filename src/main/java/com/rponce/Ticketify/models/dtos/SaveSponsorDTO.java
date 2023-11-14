package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveSponsorDTO {
	@NotEmpty
	private String id;

	@NotEmpty
	private String sponsor;

	@NotEmpty
	private String event;
}
