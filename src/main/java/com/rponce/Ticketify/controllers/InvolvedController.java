package com.rponce.Ticketify.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.SaveInvolvedDTO;
import com.rponce.Ticketify.models.dtos.PageDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Involved;
import com.rponce.Ticketify.services.EventService;
import com.rponce.Ticketify.services.InvolvedService;

@RestController
@RequestMapping("/involved")
@CrossOrigin("*")
public class InvolvedController {
	
	@Autowired
	private InvolvedService involvedService;
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/")
	public ResponseEntity<?> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Page<Involved> involvedList = involvedService.findAll(page, size);
		PageDTO<Involved> response = new PageDTO<>(
					involvedList.getContent(),
					involvedList.getNumber(),
					involvedList.getSize(),
					involvedList.getTotalElements(),
					involvedList.getTotalPages()
				);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveInvolved(SaveInvolvedDTO info){
		
		UUID id = UUID.fromString(info.getEvent());
		
		Event event = eventService.findOneById(id);
		
		try {
			involvedService.saveInvolved(info, event);
			return new ResponseEntity<>("Involved created", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneInvolved(@PathVariable(name = "id") String id){
		Involved involved = involvedService.findOneById(id);
		
		if(involved == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(involved, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteInvolved(@PathVariable(name = "id") String id){
		Involved involved = involvedService.findOneById(id);
		
		if(involved == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try { 
			involvedService.deleteInvolved(id);
			return new ResponseEntity<>("Involved deleted", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<?> findByEvent(@PathVariable(name = "id") String id){
		Event event = eventService.findOneById(UUID.fromString(id));
		
		if(event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Involved> involved = involvedService.findByEvent(event);
		return new ResponseEntity<>(involved, HttpStatus.OK);
	}
	
	
	
	
	
}

