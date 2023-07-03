package com.rponce.Ticketify.services.implementations;

import java.util.List;
import java.util.UUID;
import java.text.SimpleDateFormat;  
import java.util.Date;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveEventDTO;
import com.rponce.Ticketify.models.dtos.UpdateEventDTO;
import com.rponce.Ticketify.models.entities.Category;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.repositories.EventRepository;
import com.rponce.Ticketify.repositories.CategoryRepository;
import com.rponce.Ticketify.services.EventService;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveEvent(SaveEventDTO info, Category category) throws Exception {
		
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(info.getDate());
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		
		Date hour = (Date)formatter.parse(info.getHour());
		
		Event event = new Event(
				info.getTitle(),
				true,
				info.getImage(),
				date,
				hour,
				category,
				info.getPlace(),
				info.getAddress()
				);
		
		eventRepository.save(event);
	}

	@Override
	public Event findOneById(UUID id) {
		try {
			return eventRepository.findById(id)
					.orElse(null);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public List<Event> findAllEvents() {
		return eventRepository.findAll();
	}

	@Override
	public List<Event> findByCategory(Category category) {
		return eventRepository.findByCategory(category);
	}

	@Override
	public Page<Event> findAllEvents(String title, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
		return eventRepository.findByTitleContains(title, pageable);
	}

	@Override
	public Event findByTitle(String title) {
		try {
			return eventRepository.findByTitle(title);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public void switchEventStatus(Event event) throws Exception {
		event.setStatus(!event.getStatus());
		eventRepository.save(event);
	}

	@Override
	public void updateEvent(Event event, UpdateEventDTO newEvent) throws Exception {
		if(newEvent.getNewTitle() != null)
			event.setTitle(newEvent.getNewTitle());
		
		if(newEvent.getImage() != null)
			event.setImage(newEvent.getImage());
		
		if(newEvent.getHour() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			Date hour = (Date)formatter.parse(newEvent.getHour());
			event.setHour(hour);
		}
		
		if(newEvent.getDate() != null) {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(newEvent.getDate());
			event.setDate(date);
		}
			
		
		if(newEvent.getCategory() != null) {
			Category category = categoryRepository.findByCategory(newEvent.getCategory());
			event.setCategory(category);
		}
		
		if(newEvent.getPlace() != null)
			event.setPlace(newEvent.getPlace());
			
		if(newEvent.getAddress() != null)
			event.setAddress(newEvent.getAddress());
		
		eventRepository.save(event);
		
	}
	
}
