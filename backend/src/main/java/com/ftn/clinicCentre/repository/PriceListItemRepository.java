package com.ftn.clinicCentre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.clinicCentre.entity.PriceListItem;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long>{
	
	PriceListItem findPriceListItemByName(String name);

}
