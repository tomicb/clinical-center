package com.ftn.clinicCentre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.clinicCentre.entity.PriceListItem;
import com.ftn.clinicCentre.repository.PriceListItemRepository;
import com.ftn.clinicCentre.service.PriceListItemService;

@Service
public class PriceListItemServiceImpl implements PriceListItemService {
	
	@Autowired
	PriceListItemRepository repo;

	@Override
	public PriceListItem findPriceListItemByName(String name) {
		// TODO Auto-generated method stub
		return repo.findPriceListItemByName(name);
	}

}
