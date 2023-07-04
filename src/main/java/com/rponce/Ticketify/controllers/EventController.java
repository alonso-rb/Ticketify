package com.rponce.Ticketify.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.SaveEventDTO;
import com.rponce.Ticketify.models.dtos.UpdateEventDTO;
import com.rponce.Ticketify.models.dtos.FindEventDTO;
import com.rponce.Ticketify.models.dtos.PageDTO;
import com.rponce.Ticketify.models.entities.Category;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.services.CategoryService;
import com.rponce.Ticketify.services.EventService;
import com.rponce.Ticketify.services.TierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
@CrossOrigin("*")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TierService tierService;
	
	@GetMapping("/all")
	public ResponseEntity<?> findAllEvents(@RequestParam(defaultValue = "") String title,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		Page<Event> events = eventService.findAllEvents(title, page, size);
		PageDTO<Event> response = new PageDTO<> (
					events.getContent(), 
					events.getNumber(), 
					events.getSize(),
					events.getTotalElements(),
					events.getTotalPages()
				);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveEvent(@ModelAttribute @Valid SaveEventDTO info){
		Category category = categoryService.findOneById(info.getCategory());
		
		if(category== null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			eventService.saveEvent(info, category);
			return new ResponseEntity<>("Event created", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updateEvent(@ModelAttribute UpdateEventDTO info, @PathVariable(name = "id") String id){

		UUID id_event = UUID.fromString(id);

		Event event = eventService.findOneById(id_event);
		
		if(event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			eventService.updateEvent(event, info);
			return new ResponseEntity<>("Event updated", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/switch/{id}")
	public ResponseEntity<?> switchEvent(@PathVariable(name = "id") String id){
		UUID id_event = UUID.fromString(id);

		Event event = eventService.findOneById(id_event);
		
		if(event == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			eventService.switchEventStatus(event);
			return new ResponseEntity<>("Event has switched", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneEvent(@PathVariable(name = "id") String id){
		UUID id_event = UUID.fromString(id);

		Event findevent = eventService.findOneById(id_event);

		if(findevent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Tier> tiers = tierService.findByEvent(findevent);

		FindEventDTO event = new FindEventDTO(
				findevent,
				tiers
				);
		
		return new ResponseEntity<>(event, HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<?> findByCategory(@PathVariable(name = "id") String id) {
		Category category = categoryService.findOneById(id);
		
		if(category== null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<Event> events = eventService.findByCategory(category);
		
		return new ResponseEntity<>(events, HttpStatus.OK);
	}
	
}
