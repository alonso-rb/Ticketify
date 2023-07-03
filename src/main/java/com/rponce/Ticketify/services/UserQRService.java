package com.rponce.Ticketify.services;

import java.util.List;

import com.rponce.Ticketify.models.dtos.SaveUserQRDTO;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserQR;

public interface UserQRService {
	
	public void SaveUserQR(SaveUserQRDTO info, User userId) throws Exception;
	List<UserQR> GetUserQRByUserId(User user);
	UserQR GetUserQRByQR(String qr);

}
