package com.rponce.Ticketify.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rponce.Ticketify.models.dtos.SaveSponsorDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Sponsor;

public interface SponsorService {
	void saveSponsor(SaveSponsorDTO info, Event event) throws Exception;
	Sponsor findOneById(String id);
	List<Sponsor> findAllSponsors();
	void deleteSponsor(String id) throws Exception;
	List<Sponsor> findByEvent(Event event);
	Page<Sponsor> findAll(int page, int size);
}
