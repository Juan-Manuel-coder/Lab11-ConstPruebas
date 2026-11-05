package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testFindVetById() {
        String FIRST_NAME = "James";
        Integer ID = 1;

        Vet vet = null;
        try {
            vet = vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(vet);
        assertEquals(FIRST_NAME, vet.getFirstName());
    }

    @Test
    public void testCreateVet() {
        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Ramirez";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);
        Vet vetCreated = vetService.create(vet);

        assertNotNull(vetCreated.getId());
        assertEquals(FIRST_NAME, vetCreated.getFirstName());
        assertEquals(LAST_NAME, vetCreated.getLastName());
    }

    @Test
    public void testUpdateVet() {
        String FIRST_NAME = "James";
        String LAST_NAME = "Carter";
        String UP_LAST_NAME = "CarterUpdated";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);
        Vet vetCreated = vetService.create(vet);

        vetCreated.setLastName(UP_LAST_NAME);
        Vet vetUpdated = vetService.update(vetCreated);

        assertEquals(UP_LAST_NAME, vetUpdated.getLastName());
    }

    @Test
    public void testDeleteVet() {
        String FIRST_NAME = "Juan";
        String LAST_NAME = "Perez";

        Vet vet = new Vet(FIRST_NAME, LAST_NAME);
        Vet vetCreated = vetService.create(vet);

        try {
            vetService.delete(vetCreated.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetNotFoundException.class, () -> {
            vetService.findById(vetCreated.getId());
        });
    }
}