package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testFindSpecialtyById() {
        String NAME = "radiology";
        Integer ID = 1;

        Specialty specialty = null;
        try {
            specialty = specialtyService.findById(ID);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(specialty);
        assertEquals(NAME, specialty.getName());
    }

    @Test
    public void testCreateSpecialty() {
        String NAME = "dermatology";

        Specialty specialty = new Specialty(NAME);
        Specialty specialtyCreated = specialtyService.create(specialty);

        assertNotNull(specialtyCreated.getId());
        assertEquals(NAME, specialtyCreated.getName());
    }

    @Test
    public void testUpdateSpecialty() {
        String NAME = "cardiology";
        String UP_NAME = "cardiologyUpdated";

        Specialty specialty = new Specialty(NAME);
        Specialty specialtyCreated = specialtyService.create(specialty);

        specialtyCreated.setName(UP_NAME);
        Specialty specialtyUpdated = specialtyService.update(specialtyCreated);

        assertEquals(UP_NAME, specialtyUpdated.getName());
    }

    @Test
    public void testDeleteSpecialty() {
        String NAME = "neurology";

        Specialty specialty = new Specialty(NAME);
        Specialty specialtyCreated = specialtyService.create(specialty);

        try {
            specialtyService.delete(specialtyCreated.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(SpecialtyNotFoundException.class, () -> {
            specialtyService.findById(specialtyCreated.getId());
        });
    }
}