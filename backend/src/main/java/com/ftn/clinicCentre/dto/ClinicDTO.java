package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.PriceListItem;

import java.util.ArrayList;
import java.util.List;

public class ClinicDTO {
	
	private Long id;
	private String name;
	private String address;
	private String description;
	private Double rating;
	private List<PriceListItem> priceList = new ArrayList<>();
	
	public ClinicDTO() {}


	public ClinicDTO(Long id, String name, String address, String description, Double rating, List<PriceListItem> priceList) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.description = description;
		this.priceList = priceList;
	}
	
	public ClinicDTO(Clinic clinic) {
		this.id = clinic.getId();
		this.name = clinic.getName();
		this.address = clinic.getAddress();
		this.rating = clinic.getRating();
		this.description = clinic.getDescription();
		this.priceList = clinic.getPriceList();
	}

	public ClinicDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;

	}

	public ClinicDTO(Long id, String name, String address, Double rating) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rating = rating;
	}

	public ClinicDTO(Long id, String name, String address, String description, List<PriceListItem> priceList) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.priceList = priceList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public List<PriceListItem> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<PriceListItem> priceList) {
		this.priceList = priceList;
	}
}
