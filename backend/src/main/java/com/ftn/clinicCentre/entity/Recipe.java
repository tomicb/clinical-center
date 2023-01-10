package com.ftn.clinicCentre.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    private ERecipeStatus status;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Medication> medication;

    @ManyToOne
    @JoinColumn(name = "jmbg")
    private Nurse nurse;

    @OneToOne(mappedBy = "recipe")
    private Examination examination;

    public Recipe() { }

    public Recipe(ERecipeStatus status, LocalDateTime created, Nurse nurse) {
        this.status = status;
        this.created = created;
        this.nurse = nurse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERecipeStatus getStatus() {
        return status;
    }

    public void setStatus(ERecipeStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", status=" + status +
                ", created=" + created +
                ", medication=" + medication +
                ", nurse=" + nurse +
                '}';
    }
}
