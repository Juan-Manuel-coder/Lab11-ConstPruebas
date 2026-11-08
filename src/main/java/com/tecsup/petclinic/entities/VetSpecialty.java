package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vet_specialties")
@Data
@NoArgsConstructor
@IdClass(VetSpecialtyId.class)
public class VetSpecialty {

    @Id
    @Column(name = "vet_id")
    private Integer vetId;

    @Id
    @Column(name = "specialty_id")
    private Integer specialtyId;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "notes")
    private String notes;

    public VetSpecialty(Integer vetId, Integer specialtyId, Integer yearsExperience, String notes) {
        this.vetId = vetId;
        this.specialtyId = specialtyId;
        this.yearsExperience = yearsExperience;
        this.notes = notes;
    }
}
