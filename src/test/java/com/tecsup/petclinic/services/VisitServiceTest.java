package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    // 1. Prueba de Búsqueda por ID (Read)
    @Test
    public void testFindVisitById() {
        Integer ID = 1;
        String DESCRIPTION = "rabies shot";

        Visit visit = null;
        try {
            visit = visitService.findById(ID);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(visit);
        assertEquals(DESCRIPTION, visit.getDescription());
    }

    // 2. Prueba de Creación (Create)
    @Test
    public void testCreateVisit() {
        Integer PET_ID = 1;
        LocalDate VISIT_DATE = LocalDate.now();
        String DESCRIPTION = "routine checkup";

        Visit visit = new Visit(PET_ID, VISIT_DATE, DESCRIPTION);
        Visit visitCreated = visitService.create(visit);

        assertNotNull(visitCreated.getId());
        assertEquals(PET_ID, visitCreated.getPetId());
        assertEquals(VISIT_DATE, visitCreated.getVisitDate());
        assertEquals(DESCRIPTION, visitCreated.getDescription());
    }

    // 3. Prueba de Actualización (Update)
    @Test
    public void testUpdateVisit() {
        Integer PET_ID = 2;
        LocalDate VISIT_DATE = LocalDate.now();
        String DESCRIPTION = "dental cleaning";
        String UP_DESCRIPTION = "dental cleaning completed";

        Visit visit = new Visit(PET_ID, VISIT_DATE, DESCRIPTION);
        Visit visitCreated = visitService.create(visit);

        visitCreated.setDescription(UP_DESCRIPTION);
        Visit visitUpdated = visitService.update(visitCreated);

        assertEquals(UP_DESCRIPTION, visitUpdated.getDescription());
    }

    // 4. Prueba de Eliminación (Delete)
    @Test
    public void testDeleteVisit() {
        Integer PET_ID = 3;
        LocalDate VISIT_DATE = LocalDate.now();
        String DESCRIPTION = "vaccination";

        Visit visit = new Visit(PET_ID, VISIT_DATE, DESCRIPTION);
        Visit visitCreated = visitService.create(visit);

        try {
            visitService.delete(visitCreated.getId());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> {
            visitService.findById(visitCreated.getId());
        });
    }
}