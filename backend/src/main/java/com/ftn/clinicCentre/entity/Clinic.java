package com.ftn.clinicCentre.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinic_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PriceListItem> priceList;

    @Column(name = "rating", nullable = false)
    private Double rating;

    public Clinic() {}

    public Clinic(String name, String address, String description, List<PriceListItem> priceList, Double rating) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.priceList = priceList;
        this.rating = rating;
    }

    public Clinic(Long id, String name, String address, String description, List<PriceListItem> priceList, Double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.priceList = priceList;
        this.rating = rating;
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

    public List<PriceListItem> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceListItem> priceList) {
        this.priceList = priceList;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", priceList=" + priceList +
                ", rating=" + rating +
                '}';
    }
}
