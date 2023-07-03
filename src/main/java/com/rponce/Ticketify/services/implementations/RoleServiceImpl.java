package com.rponce.Ticketify.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveRoleDTO;
import com.rponce.Ticketify.models.entities.Role;
import com.rponce.Ticketify.repositories.RoleRepository;
import com.rponce.Ticketify.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void SaveRole(SaveRoleDTO info) throws Exception {
		
		Role newRole = new Role ();
		newRole.setId(info.getRoleId());
		newRole.setRole(info.getRoleName());

		roleRepository.save(newRole);
	}

	@Override
	public Role GetRoleByName(String roleName) {
		return roleRepository.findOneByRole(roleName);
	}

	@Override
	public List<Role> GetAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public void DeleteRoleByName(String roleName) throws Exception {
		Role roleToDelete = roleRepository.findOneByRole(roleName);
		
		roleRepository.delete(roleToDelete);
		
	}

	
	

}
