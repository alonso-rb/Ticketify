package com.rponce.Ticketify.models.dtos;

import com.rponce.Ticketify.models.entities.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
		
	private String token;
	
	public TokenDTO(Token token) {
		this.token = token.getContent();
	}

}
