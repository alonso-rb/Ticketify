package com.rponce.Ticketify.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.SaveTierDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.services.EventService;
import com.rponce.Ticketify.services.TierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tiers")
@CrossOrigin("*")
public class TierController {

	@Autowired
	private TierService tierService;
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		List<Tier> tiers = tierService.findAllTier();
		return new ResponseEntity<>(tiers, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveTier(@ModelAttribute @Valid SaveTierDTO info) {
		
		UUID id = UUID.fromString(info.getEvent());
		
		Event event = eventService.findOneById(id);
		
		try {
			tierService.saveTier(info, event);
			return new ResponseEntity<>("Tier created", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneTier(@PathVariable(name = "id") String id){
		Tier tier = tierService.findOneById(id);
		
		if(tier == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(tier, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTier(@PathVariable(name = "id") String id) {
		Tier tier = tierService.findOneById(id);
		
		if(tier == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			tierService.deleteTier(id);
			return new ResponseEntity<>("Tier deleted", HttpStatus.OK);
		} catch(Exception error) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<?> findByEvent(@PathVariable(name = "id") String id){
		Event event = eventService.findOneById(UUID.fromString(id));
		
		if(event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Tier> tiers = tierService.findByEvent(event);
		return new ResponseEntity<>(tiers, HttpStatus.OK);
	}
	
	@GetMapping("/tier/{tier}")
	public ResponseEntity<?> findByTier(@PathVariable(name = "tier") String tier) {
		Tier tierFound = tierService.findByTier(tier);
		
		if(tierFound == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(tierFound, HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
