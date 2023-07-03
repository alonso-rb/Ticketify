package com.rponce.Ticketify.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Role;

public interface RoleRepository extends ListCrudRepository<Role, String> {
	
	Role findOneByRole(String name);

}
