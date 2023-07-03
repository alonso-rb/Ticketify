package com.rponce.Ticketify.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rponce.Ticketify.models.dtos.SaveInvolvedDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Involved;

public interface InvolvedService {
	void saveInvolved(SaveInvolvedDTO info, Event event) throws Exception;
	Involved findOneById(String id);
	List<Involved> findAllInvolved();
	void deleteInvolved(String id) throws Exception;
	List<Involved> findByEvent(Event event);
	Page<Involved> findAll(int page, int size);
}
