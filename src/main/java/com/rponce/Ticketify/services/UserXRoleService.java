package com.rponce.Ticketify.services;

import java.util.Date;
import java.util.List;

import com.rponce.Ticketify.models.entities.Role;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserXRole;

public interface UserXRoleService {
	
	public void CreateUserXRole(Date date, User user, Role role, Boolean status) throws Exception;
	public List<UserXRole> getAllUserXRole();
	List<UserXRole> findByUser(User user);
	List<UserXRole> findByRole(Role role);
	public void DeleteRolesFromUser(User user, Role role) throws Exception;

}