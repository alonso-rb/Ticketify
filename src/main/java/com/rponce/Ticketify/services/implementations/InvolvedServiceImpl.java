package com.rponce.Ticketify.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveInvolvedDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Involved;
import com.rponce.Ticketify.repositories.InvolvedRepository;
import com.rponce.Ticketify.services.InvolvedService;

import jakarta.transaction.Transactional;

@Service
public class InvolvedServiceImpl implements InvolvedService{
	
	@Autowired
	private InvolvedRepository involvedRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveInvolved(SaveInvolvedDTO info, Event event) throws Exception {
		
		Involved involved = new Involved(
				info.getId(),
				info.getInvolved(),
				event
				);
		involvedRepository.save(involved);
	}

	@Override
	public Involved findOneById(String id) {
		return involvedRepository.findById(id)
				.orElse(null);
	}

	@Override
	public List<Involved> findAllInvolved() {
		return involvedRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteInvolved(String id) throws Exception {
		involvedRepository.deleteById(id);
	}

	@Override
	public List<Involved> findByEvent(Event event) {
		try {
			return involvedRepository.findAllByEvent(event);
		}catch(Exception error) {
			return null;
		}
		
	}

	@Override
	public Page<Involved> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("involved"));
		return involvedRepository.findAll(pageable);
	}

}
