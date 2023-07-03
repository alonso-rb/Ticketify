package com.rponce.Ticketify.services;

import java.util.List;

import com.rponce.Ticketify.models.dtos.SaveRoleDTO;
import com.rponce.Ticketify.models.entities.Role;

public interface RoleService {
	
	public void SaveRole(SaveRoleDTO info) throws Exception;
	Role GetRoleByName(String roleName);
	List<Role> GetAllRoles();
	public void DeleteRoleByName(String roleName) throws Exception;

}
