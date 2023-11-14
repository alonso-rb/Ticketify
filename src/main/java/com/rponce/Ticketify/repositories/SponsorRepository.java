package com.rponce.Ticketify.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor, String>{
	public List<Sponsor> findAllByEvent(Event event);
	Page<Sponsor> findAll(Pageable pageable);
}
