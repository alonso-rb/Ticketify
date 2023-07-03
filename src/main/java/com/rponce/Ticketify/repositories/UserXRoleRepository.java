package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Role;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserXRole;

public interface UserXRoleRepository extends ListCrudRepository<UserXRole, UUID>{
	
	List<UserXRole> findAllByUser(User user);
	List<UserXRole> findAllByRole(Role role);
	UserXRole findOneByUserAndRole(User user, Role role);

}
