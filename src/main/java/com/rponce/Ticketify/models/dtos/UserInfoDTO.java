package com.rponce.Ticketify.models.dtos;

import java.util.List;

import com.rponce.Ticketify.models.entities.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDTO {
	
	private List<String> RoleName;
	
	private String token;

	private String id;
}
