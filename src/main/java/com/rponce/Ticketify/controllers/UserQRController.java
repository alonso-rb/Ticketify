package com.rponce.Ticketify.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.FindUserQRByUserDTO;
import com.rponce.Ticketify.models.dtos.SaveUserQRDTO;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserQR;
import com.rponce.Ticketify.services.UserQRService;
import com.rponce.Ticketify.services.UserService;
import com.rponce.Ticketify.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/userqr")
@CrossOrigin("*")
public class UserQRController {
	
	@Autowired
	private UserQRService userQRService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	
	@PostMapping("/generate")
	private ResponseEntity<?> SaveUserQR(@ModelAttribute @Valid  SaveUserQRDTO info, String user, 
			BindingResult validations){
		
		Integer hashQR = new Date().hashCode();
		String begginer = "QR-";
		String qr = begginer.concat(hashQR.toString());
		
		Date date = new Date();
		
		info.setQr(qr);
		info.setCreationDate(date);
		info.setActive(true);
		
		User userToAssign = userService.findUserAuthenticated();
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()),
			HttpStatus.BAD_REQUEST);
		}

		
		try {
			userQRService.SaveUserQR(info, userToAssign);
			return new ResponseEntity<>(info, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{userid}")
	private ResponseEntity<?> GetUserQRByUser(@PathVariable(name = "userid") String id){
		
		User user = userService.FindOneUserById(id);
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<FindUserQRByUserDTO> userQRsToShow = new ArrayList<>();
	
		List<UserQR> userQR = userQRService.GetUserQRByUserId(user);
		
		userQR.stream()
		.forEach(q-> {
			FindUserQRByUserDTO userQRToShow = new FindUserQRByUserDTO();
			userQRToShow.setQr(q.getQr());
			userQRToShow.setCreationDate(q.getCreationDate());
			userQRToShow.setActive(q.getStatus());
			userQRToShow.setUsername(q.getUserID().getEmail());
			
			userQRsToShow.add(userQRToShow);
		});
		
		return new ResponseEntity<>(userQRsToShow, HttpStatus.OK);
	}

	@GetMapping("/user/{userqr}")
	private ResponseEntity<?> GetUserByUserQR(@PathVariable(name = "userqr") String qr){
		
		UserQR userQr = userQRService.GetUserQRByQR(qr);
		User user = userQr.getUserID();
		
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
}
