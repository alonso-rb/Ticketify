package com.rponce.Ticketify.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Involved;

public interface InvolvedRepository extends JpaRepository<Involved, String>{
	public List<Involved> findAllByEvent(Event event);
	Page<Involved> findAll(Pageable pageable);
}