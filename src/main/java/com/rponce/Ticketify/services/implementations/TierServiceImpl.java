package com.rponce.Ticketify.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveTierDTO;
import com.rponce.Ticketify.models.entities.Event;
import com.rponce.Ticketify.models.entities.Tier;
import com.rponce.Ticketify.repositories.TierRepository;
import com.rponce.Ticketify.services.TierService;

import jakarta.transaction.Transactional;

@Service
public class TierServiceImpl implements TierService{

	@Autowired
	private TierRepository tierRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveTier(SaveTierDTO info, Event event) throws Exception {
		Tier tier = new Tier(
				info.getTier(),
				info.getPrice(),
				info.getCapacity(),
				event
				);
		tierRepository.save(tier);
	}

	@Override
	public Tier findOneById(String id) {
		return tierRepository.findById(UUID.fromString(id))
				.orElse(null);
	}

	@Override
	public List<Tier> findAllTier() {
		return tierRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteTier(String id) throws Exception{
		tierRepository.deleteById(UUID.fromString(id));
	}

	@Override
	public List<Tier> findByEvent(Event event) {
		try {
			return tierRepository.findAllByEvent(event);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public Tier findByTier(String tier) {
		try {
			return tierRepository.findByTier(tier);
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public void updateTier(Tier tier) {
		tierRepository.save(tier);
	}

	@Override
	public void updateTierCapacity(Tier tier, int cantidad) {
		//actualizo la capacidad de la tier
				tier.setCapacity(tier.getCapacity() - cantidad);
				//guardo las modificaciones de la tier en la base de datos
				tierRepository.save(tier);
	}
	
}
