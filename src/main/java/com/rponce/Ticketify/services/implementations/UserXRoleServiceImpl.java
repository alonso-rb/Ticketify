package com.rponce.Ticketify.services.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.entities.Role;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserXRole;
import com.rponce.Ticketify.repositories.UserXRoleRepository;
import com.rponce.Ticketify.services.UserXRoleService;

import jakarta.transaction.Transactional;

@Service
public class UserXRoleServiceImpl implements UserXRoleService {

	@Autowired
	private UserXRoleRepository userXRolerepository;
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void CreateUserXRole(Date date, User user, Role role, Boolean status) throws Exception {
		UserXRole newUserXRole = new UserXRole();
		
		newUserXRole.setAssignationDate(date);
		newUserXRole.setRole(role);
		newUserXRole.setUser(user);
		newUserXRole.setStatus(status);
		
		userXRolerepository.save(newUserXRole);
		
	}

	@Override
	public List<UserXRole> getAllUserXRole() {
		return userXRolerepository.findAll();
	}

	@Override
	public List<UserXRole> findByUser(User user) {
		try {
			return userXRolerepository.findAllByUser(user);
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<UserXRole> findByRole(Role role) {
		try {
			return userXRolerepository.findAllByRole(role);
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public void DeleteRolesFromUser(User user, Role role) throws Exception {
		UserXRole roleToDelete = userXRolerepository.findOneByUserAndRole(user, role);
		
		userXRolerepository.deleteById(roleToDelete.getUuid());
		
	}

}
