package com.rponce.Ticketify.models.dtos;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEventDTO {
	private String newTitle;
	private String image;
	private String date;
	private String hour;
	private String category;
	private String place;
	private String address;
}
