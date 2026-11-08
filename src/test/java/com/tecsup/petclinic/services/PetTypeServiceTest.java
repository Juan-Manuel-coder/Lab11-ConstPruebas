package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PetTypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    // 1. Prueba de Búsqueda por ID (Read)
    @Test
    public void testFindPetTypeById() {
        Integer ID = 1;
        String NAME = "cat";

        PetType petType = null;
        try {
            petType = petTypeService.findById(ID);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(petType);
        assertEquals(NAME, petType.getName());
    }

    // 2. Prueba de Creación (Create)
    @Test
    public void testCreatePetType() {
        String NAME = "hamster";

        PetType petType = new PetType(NAME);
        PetType petTypeCreated = petTypeService.create(petType);

        assertNotNull(petTypeCreated.getId());
        assertEquals(NAME, petTypeCreated.getName());
    }

    // 3. Prueba de Actualización (Update)
    @Test
    public void testUpdatePetType() {
        String NAME = "bird";
        String UP_NAME = "parrot";

        PetType petType = new PetType(NAME);
        PetType petTypeCreated = petTypeService.create(petType);

        petTypeCreated.setName(UP_NAME);
        PetType petTypeUpdated = petTypeService.update(petTypeCreated);

        assertEquals(UP_NAME, petTypeUpdated.getName());
    }

    // 4. Prueba de Eliminación (Delete)
    @Test
    public void testDeletePetType() {
        String NAME = "reptile";

        PetType petType = new PetType(NAME);
        PetType petTypeCreated = petTypeService.create(petType);

        try {
            petTypeService.delete(petTypeCreated.getId());
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(PetTypeNotFoundException.class, () -> {
            petTypeService.findById(petTypeCreated.getId());
        });
    }
}