package com.rponce.Ticketify.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveSponsorDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Sponsor;
import com.rponce.Ticketify.repositories.SponsorRepository;
import com.rponce.Ticketify.services.SponsorService;

import jakarta.transaction.Transactional;

@Service
public class SponsorServiceImpl implements SponsorService{
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveSponsor(SaveSponsorDTO info, Event event) throws Exception {
		
		Sponsor involved = new Sponsor(
				info.getId(),
				info.getSponsor(),
				event
				);
		sponsorRepository.save(involved);
	}

	@Override
	public Sponsor findOneById(String id) {
		return sponsorRepository.findById(id)
				.orElse(null);
	}

	@Override
	public List<Sponsor> findAllSponsors() {
		return sponsorRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteSponsor(String id) throws Exception {
		sponsorRepository.deleteById(id);
	}

	@Override
	public List<Sponsor> findByEvent(Event event) {
		try {
			return sponsorRepository.findAllByEvent(event);
		} catch(Exception error) {
			return null;
		}
	}

	@Override
	public Page<Sponsor> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("sponsor"));
		return sponsorRepository.findAll(pageable);
	}
}

