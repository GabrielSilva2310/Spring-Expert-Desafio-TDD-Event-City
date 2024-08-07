package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event update=repository.getReferenceById(id);
			copyDtoToEntity(update, dto);
			update=repository.save(update);
			return new EventDTO(update);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
	}
	
	public void copyDtoToEntity(Event entity, EventDTO dto) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		entity.setCity(new City(dto.getCityId(), null));
	}
}
