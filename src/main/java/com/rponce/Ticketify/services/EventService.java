package com.rponce.Ticketify.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.rponce.Ticketify.models.dtos.SaveEventDTO;
import com.rponce.Ticketify.models.dtos.UpdateEventDTO;
import com.rponce.Ticketify.models.entities.Category;
import com.rponce.Ticketify.models.entities.Event;

public interface EventService {
	void saveEvent(SaveEventDTO info, Category cateogry) throws Exception;
	Event findOneById(UUID id);
	Event findByTitle(String title);
	void switchEventStatus(Event event) throws Exception;
	void updateEvent(Event event, UpdateEventDTO newEvent) throws Exception;
	List<Event> findAllEvents();
	List<Event> findByCategory(Category category);
	Page<Event> findAllEvents(String title, int page, int size);
}