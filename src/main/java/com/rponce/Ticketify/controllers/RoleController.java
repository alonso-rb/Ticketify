package com.rponce.Ticketify.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.SaveRoleDTO;
import com.rponce.Ticketify.models.dtos.UserRolesDTO;
import com.rponce.Ticketify.models.entities.Role;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserXRole;
import com.rponce.Ticketify.services.RoleService;
import com.rponce.Ticketify.services.UserService;
import com.rponce.Ticketify.services.UserXRoleService;
import com.rponce.Ticketify.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/role")
@CrossOrigin("*")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private UserXRoleService userxroleService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/save")
	private ResponseEntity<?> SaveRole (@ModelAttribute @Valid SaveRoleDTO role, BindingResult validations) {
		
		Role existingRole = roleService.GetRoleByName(role.getRoleName());
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST
					);
		}
		
		if(existingRole != null) {
			return new ResponseEntity<>(
					HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			roleService.SaveRole(role);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/all")
	private ResponseEntity<?> GetAllRoles(){
		
		if(roleService.GetAllRoles()==null) {
			return new ResponseEntity<>(
					HttpStatus.NOT_FOUND
					);
		}
		
		return new ResponseEntity<>(
				roleService.GetAllRoles(), HttpStatus.OK
				);
	}
	
	@GetMapping("/{name}")
	private ResponseEntity<?> GetRoleByName (@PathVariable(name = "name") String name){
		
		return new ResponseEntity<>(
				roleService.GetRoleByName(name), HttpStatus.OK
				);
	}
	
	@DeleteMapping("/delete/{name}")
	private ResponseEntity<?> DeleteRoleByName (@PathVariable(name = "name") String name){
		
		try {
			roleService.DeleteRoleByName(name);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/userroles/{userid}")
	private ResponseEntity<?> FindAllRolesByUser (@PathVariable(name = "userid") String userid){
		
		User user = userService.FindOneUserById(userid);
		
		List<UserRolesDTO> listToShow = new ArrayList<>();
		List<UserXRole> userxroleList = userxroleService.findByUser(user);
		
		userxroleList.forEach(u-> {
			UserRolesDTO userRolesList = new UserRolesDTO();
			userRolesList.setUserId(u.getUser().getUuid().toString());
			userRolesList.setUserName(u.getUser().getFirstname().concat(" ").concat(u.getUser().getLastname()));
			userRolesList.setEmail(u.getUser().getEmail());
			userRolesList.setRoleAssigned(u.getRole().getRole());
			userRolesList.setDateAssigned(u.getAssignationDate());
			userRolesList.setStatus(u.getStatus());
			
			listToShow.add(userRolesList);
		});
		
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				listToShow, HttpStatus.OK
				);
				
	}

}
