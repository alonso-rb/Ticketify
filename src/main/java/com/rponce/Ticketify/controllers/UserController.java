package com.rponce.Ticketify.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.AssignRolesToUserDTO;
import com.rponce.Ticketify.models.dtos.AuthUserDTO;
import com.rponce.Ticketify.models.dtos.RecuperatePasswordDTO;
import com.rponce.Ticketify.models.dtos.ReturnUserRoleDTO;
import com.rponce.Ticketify.models.dtos.SaveUserDTO;
import com.rponce.Ticketify.models.dtos.TokenDTO;
import com.rponce.Ticketify.models.dtos.UpdatePasswordDTO;
import com.rponce.Ticketify.models.dtos.UserInfoDTO;
import com.rponce.Ticketify.models.dtos.UserRolesDTO;
import com.rponce.Ticketify.models.entities.Role;
import com.rponce.Ticketify.models.entities.Token;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserXRole;
import com.rponce.Ticketify.services.RoleService;
import com.rponce.Ticketify.services.UserService;
import com.rponce.Ticketify.services.UserXRoleService;
import com.rponce.Ticketify.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired 
	private UserXRoleService userxroleService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private String recoverCode;

	@PostMapping("/auth/signup")
	private ResponseEntity<?> SignUpUser (@ModelAttribute @Valid SaveUserDTO info, BindingResult validations){
		
		User existingUser = userService.FindOneUserByEmail(info.getEmail());
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
						errorHandler.mapErrors(validations.getFieldErrors()),
						HttpStatus.BAD_REQUEST
					);
		}
		
		if(existingUser != null) {
			return new ResponseEntity<>(
					HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			userService.SaveUser(info);
			User userToAssignUser = userService.FindOneUserByEmail(info.getEmail());
			Role role = roleService.GetRoleByName("User");
			userxroleService.CreateUserXRole(new Date(), userToAssignUser, role);
			return new ResponseEntity<>(
					HttpStatus.CREATED
					);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all")
	private ResponseEntity<?> GetAllUsers(){
		List<User> allUsers = userService.GetAllUsers();
		return new ResponseEntity<>(
				allUsers, HttpStatus.OK
				);
	}
	
	@GetMapping("/id/{id}")
	private ResponseEntity<?> GetUserById(@PathVariable(name = "id") UUID id){
		
		User userToFind = userService.FindOneUserById(id.toString());
		
		return new ResponseEntity<>(
				userToFind, HttpStatus.OK
				);
	}
	
	@GetMapping("/email/{email}")
	private ResponseEntity<?> GetUserByEmail(@PathVariable(name = "email") String email){
		
		User userToFind = userService.FindOneUserByEmail(email);
		
		return new ResponseEntity<>(
				userToFind, HttpStatus.OK
				);
	}
	
	@PostMapping("/deactivate/{id}")
	private ResponseEntity<?> DeactivateUser(@PathVariable(name = "id") UUID id){
		
		User userToDeactivate = userService.FindOneUserById(id.toString());
		
		try {
			userService.DeactivateUser(userToDeactivate);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/auth/signin")
	private ResponseEntity<?> AuthUser(@ModelAttribute @Valid AuthUserDTO info, BindingResult validations){
		User userToCheck = userService.AuthUser(info);
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
						errorHandler.mapErrors(validations.getFieldErrors()),
						HttpStatus.BAD_REQUEST
					);
		}
		
		if(userToCheck == null) {
			return new ResponseEntity<>(
					HttpStatus.NOT_FOUND
					);
		}
		
		List<String> listToShow = new ArrayList<>();
		List<UserXRole> userxroleList = userxroleService.findByUser(userToCheck);
		
		userxroleList.forEach(u-> {
			ReturnUserRoleDTO userRolesList = new ReturnUserRoleDTO();
			userRolesList.setRoleName(u.getRole().getRole());
			
			listToShow.add(userRolesList.getRoleName());
		});
		
		try {
			Token token = userService.registerToken(userToCheck);
			UserInfoDTO infoUser = new UserInfoDTO();
			infoUser.setToken(token.getContent());
			infoUser.setRoleName(listToShow);
			infoUser.setId(userToCheck.getUuid().toString());
			return new ResponseEntity<>(infoUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/auth/google/signin")
	private ResponseEntity<?> AuthGoogleUser(@ModelAttribute @Valid AuthUserDTO info, BindingResult validations){
		User userToCheck = userService.FindOneUserByEmail(info.getEmail());

		if(validations.hasErrors()) {
			return new ResponseEntity<>(
						errorHandler.mapErrors(validations.getFieldErrors()),
						HttpStatus.BAD_REQUEST
					);
		}

		if(userToCheck == null) {
			return new ResponseEntity<>(
					HttpStatus.NOT_FOUND
					);
		}

		List<String> listToShow = new ArrayList<>();
		List<UserXRole> userxroleList = userxroleService.findByUser(userToCheck);

		userxroleList.forEach(u-> {
			ReturnUserRoleDTO userRolesList = new ReturnUserRoleDTO();
			userRolesList.setRoleName(u.getRole().getRole());

			listToShow.add(userRolesList.getRoleName());
		});

		try {
			Token token = userService.registerToken(userToCheck);
			UserInfoDTO infoUser = new UserInfoDTO();
			infoUser.setToken(token.getContent());
			infoUser.setRoleName(listToShow);
			infoUser.setId(userToCheck.getUuid().toString());
			return new ResponseEntity<>(infoUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/userroles/save")
	private ResponseEntity<?> AssingRoles(@ModelAttribute @Valid AssignRolesToUserDTO info, 
			BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST
					);
		}
		
		User userToAssign = userService.FindOneUserById(info.getUserCode());
		Role roleToAssign = roleService.GetRoleByName(info.getRoleName());
		
		if(userToAssign == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(roleToAssign == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	    Date date = new Date(); 
	    
	    try {
	    	userxroleService.CreateUserXRole(date, userToAssign, roleToAssign);
	    	return new ResponseEntity<>(HttpStatus.CREATED);
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
	}
	
	@PostMapping("/request-recuperate")
	private ResponseEntity<?> RequestPasswordChange(String email){
		
		
		User userToUpdate = userService.FindOneUserByEmail(email);
		
		if(userToUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Integer hash = new Date().hashCode();
		String codeRecover = "UP-".concat(hash.toString());
		
		//Sending recover Email
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ticketifysv@gmail.com");
		message.setTo(email);
		message.setSubject("Código para aprobación de cambio de contraseña");
		message.setText("Saludos, favor ingresa el código enviado a través de este correo "
				+ "para que tu cambio de contraseña sea aprobado: ".concat(codeRecover));
		mailSender.send(message);
	
		this.recoverCode = codeRecover;
		
		return new ResponseEntity<>(email, HttpStatus.OK);		
	}
	
	@PostMapping("/recuperate-password")
	private ResponseEntity<?> RecuperatePassword(@ModelAttribute @Valid RecuperatePasswordDTO info, 
			BindingResult validations) {
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST
					);
		}
		
		User userToUpdate = userService.FindOneUserByEmail(info.getEmail());
		
		if(userToUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		System.out.println(recoverCode);
		System.out.println(info.getCode());
		
		if(!recoverCode.equals(info.getCode())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			userService.RecuperatePassword(info, userToUpdate);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/update-password")
	private ResponseEntity<?> UpdatePassword(@ModelAttribute @Valid UpdatePasswordDTO info, BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST
					);
		}
		
		User userToUpdate = userService.findUserAuthenticated();
		
		try {
			userService.UpdatePassword(info, userToUpdate);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/userroles/all")
	private ResponseEntity<?> AllRolesFromUsers(){
		
		List<UserRolesDTO> listToShow = new ArrayList<>();
		List<UserXRole> userxroleList = userxroleService.getAllUserXRole();
		
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
		
		return new ResponseEntity<>(
				listToShow, HttpStatus.OK
				);
	}
	
	@GetMapping("/userroles/{rolename}")
	private ResponseEntity<?> AllUserFromOneRole(@PathVariable(name="rolename") String role){
		Role roleToSearch = roleService.GetRoleByName(role);
		
		if(roleToSearch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<UserRolesDTO> listToShow = new ArrayList<>();
		List<UserXRole> userxroleList = userxroleService.findByRole(roleToSearch);
		
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
		
		return new ResponseEntity<>(
				listToShow, HttpStatus.OK
				);
	}
	
	@DeleteMapping("/userroles/delete/{userid}/{rolename}")
	private ResponseEntity<?> DeleteRoleFromUser(@PathVariable(name = "userid") String userid, 
			@PathVariable(name = "rolename") String rolename){
		
		User user = userService.FindOneUserById(userid);
		Role roleToDelete = roleService.GetRoleByName(rolename);
		
		if(user == null || roleToDelete == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			userxroleService.DeleteRolesFromUser(user, roleToDelete);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
