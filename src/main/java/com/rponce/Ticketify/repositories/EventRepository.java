package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rponce.Ticketify.models.entities.Category;
import com.rponce.Ticketify.models.entities.Event;

public interface EventRepository extends JpaRepository<Event, UUID>{
	List<Event> findByCategory(Category category);
	Event findByTitle(String title);
	Page<Event> findByTitleContains(String title, Pageable pageable);
}
