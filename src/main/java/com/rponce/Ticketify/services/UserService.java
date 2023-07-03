package com.rponce.Ticketify.services;

import java.util.List;

import com.rponce.Ticketify.models.dtos.AuthUserDTO;
import com.rponce.Ticketify.models.dtos.RecuperatePasswordDTO;
import com.rponce.Ticketify.models.dtos.SaveUserDTO;
import com.rponce.Ticketify.models.dtos.UpdatePasswordDTO;
import com.rponce.Ticketify.models.entities.Token;
import com.rponce.Ticketify.models.entities.User;

public interface UserService {
	
	public void SaveUser(SaveUserDTO info) throws Exception;
	User AuthUser(AuthUserDTO info);
	public void DeactivateUser(User user) throws Exception;
	public void RecuperatePassword(RecuperatePasswordDTO data, User user);
	public void UpdatePassword(UpdatePasswordDTO info, User user);
	List<User> GetAllUsers();
	User FindOneUserById(String id);
	User FindOneUserByEmail(String email);
	//Token management
	Token registerToken(User user) throws Exception;
	Boolean isTokenValid(User user, String token);
	void cleanTokens(User user) throws Exception;
	User findUserAuthenticated();
	
}
