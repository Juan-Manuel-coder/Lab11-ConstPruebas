package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    // Asignacion de una especialidad a un veterinario
    @Test
    public void testCreateVetSpecialty() {
        Integer VET_ID = 1;
        Integer SPECIALTY_ID = 3;
        Integer YEARS = 4;
        String NOTES = "Assigned in test";

        VetSpecialty created = vetSpecialtyService.create(
                new VetSpecialty(VET_ID, SPECIALTY_ID, YEARS, NOTES));

        assertEquals(VET_ID, created.getVetId());
        assertEquals(SPECIALTY_ID, created.getSpecialtyId());
        assertEquals(YEARS, created.getYearsExperience());
        assertEquals(NOTES, created.getNotes());
    }

    // Busqueda de una relacion por clave compuesta (vet 2 - radiology, datos iniciales)
    @Test
    public void testFindVetSpecialtyById() {
        Integer VET_ID = 2;
        Integer SPECIALTY_ID = 1;

        VetSpecialty vetSpecialty = null;
        try {
            vetSpecialty = vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(vetSpecialty);
        assertEquals(VET_ID, vetSpecialty.getVetId());
        assertEquals(SPECIALTY_ID, vetSpecialty.getSpecialtyId());
    }

    // Busqueda de especialidades por veterinario (vet 3 tiene surgery y dentistry)
    @Test
    public void testFindSpecialtiesByVetId() {
        Integer VET_ID = 3;
        int EXPECTED_SIZE = 2;

        List<VetSpecialty> list = vetSpecialtyService.findByVetId(VET_ID);

        assertEquals(EXPECTED_SIZE, list.size());
        list.forEach(vs -> assertEquals(VET_ID, vs.getVetId()));
    }

    // Busqueda de veterinarios por especialidad (surgery = 2 -> vets 3 y 4)
    @Test
    public void testFindVetsBySpecialtyId() {
        Integer SPECIALTY_ID = 2;
        int EXPECTED_SIZE = 2;

        List<VetSpecialty> list = vetSpecialtyService.findBySpecialtyId(SPECIALTY_ID);

        assertEquals(EXPECTED_SIZE, list.size());
        list.forEach(vs -> assertEquals(SPECIALTY_ID, vs.getSpecialtyId()));
    }

    // Eliminacion de la relacion veterinario-especialidad
    @Test
    public void testDeleteVetSpecialty() {
        Integer VET_ID = 6;
        Integer SPECIALTY_ID = 3;

        vetSpecialtyService.create(new VetSpecialty(VET_ID, SPECIALTY_ID, 1, "To delete"));

        try {
            vetSpecialtyService.delete(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetSpecialtyNotFoundException.class, () -> {
            vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
        });
    }
}
