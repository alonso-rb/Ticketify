package com.rponce.Ticketify.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveEventDTO {
	@NotEmpty
	private String title;

	@NotEmpty
	private String image;

	@NotEmpty
	private String date;

	@NotEmpty
	private String hour;

	@NotEmpty
	private String category;

	@NotEmpty
	private String place;

	@NotEmpty
	private String address;

}
