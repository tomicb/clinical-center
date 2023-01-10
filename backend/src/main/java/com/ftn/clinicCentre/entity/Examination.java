package com.ftn.clinicCentre.entity;

import javax.persistence.*;

import com.ftn.clinicCentre.security.encryption.AttributeEncryptor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "examination")
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end", nullable = false)
    private LocalDateTime end;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "report", nullable = true)
    @Convert(converter = AttributeEncryptor.class)
    private String report;

    @Column(name = "title", nullable = true)
    @Convert(converter = AttributeEncryptor.class)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "examination_recipe",
            joinColumns = { @JoinColumn(name = "examination_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "recipe_id", referencedColumnName = "id") })
    private Recipe recipe;

    public Examination() {}

    public Examination(Doctor doctor, Patient patient, LocalDateTime start, LocalDateTime end, Double price, Integer discount, String report, String title) {
        this.doctor = doctor;
        this.patient = patient;
        this.start = start;
        this.end = end;
        this.price = price;
        this.discount = discount;
        this.report = report;
        this.title = title;
    }
    
    public Examination(Doctor doctor, LocalDateTime start, LocalDateTime end, String title) {
        this.doctor = doctor;
        this.patient = null;
        this.start = start;
        this.end = end;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Examination{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", start=" + start +
                ", end=" + end +
                ", price=" + price +
                ", discount=" + discount +
                ", report='" + report + '\'' +
                ", title='" + title + '\'' +
                ", recipe='" + recipe + '\'' +
                '}';
    }
}
