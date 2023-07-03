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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.SaveSponsorDTO;
import com.rponce.Ticketify.models.dtos.PageDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Sponsor;
import com.rponce.Ticketify.services.EventService;
import com.rponce.Ticketify.services.SponsorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sponsors")
@CrossOrigin("*")
public class SponsorController {
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private EventService eventService;
	
	@GetMapping("/")
	public ResponseEntity<?> findAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Page<Sponsor> sponsorList = sponsorService.findAll(page, size);
		PageDTO<Sponsor> response = new PageDTO<>(
					sponsorList.getContent(),
					sponsorList.getNumber(),
					sponsorList.getSize(),
					sponsorList.getTotalElements(),
					sponsorList.getTotalPages()
				);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveSponsor(@ModelAttribute @Valid SaveSponsorDTO info){
		
		UUID id = UUID.fromString(info.getEvent());
		Event event = eventService.findOneById(id);
		
		try {
			sponsorService.saveSponsor(info, event);
			return new ResponseEntity<>("Sponsor created", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneSponsor(@PathVariable(name = "id") String id){
		Sponsor sponsor = sponsorService.findOneById(id);
		
		if(sponsor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(sponsor, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSponsor(@PathVariable(name = "id") String id){
		Sponsor sponsor = sponsorService.findOneById(id);
		
		if(sponsor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try { 
			sponsorService.deleteSponsor(id);
			return new ResponseEntity<>("Sponsor deleted", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<?> findByEvent(@PathVariable(name = "id") String id) {
		Event event = eventService.findOneById(UUID.fromString(id));
		
		if(event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Sponsor> sponsors = sponsorService.findByEvent(event);
		return new ResponseEntity<>(sponsors, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
}
