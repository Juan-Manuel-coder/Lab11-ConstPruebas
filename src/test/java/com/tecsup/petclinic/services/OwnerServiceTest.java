package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	// 1. Prueba de Búsqueda por ID (Read)
	@Test
	public void testFindOwnerById() {
		String FIRST_NAME = "George";
		Integer ID = 1;

		Owner owner = null;
		try {
			owner = ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		assertNotNull(owner);
		assertEquals(FIRST_NAME, owner.getFirstName());
	}

	// 2. Prueba de Creación (Create)
	@Test
	public void testCreateOwner() {
		String FIRST_NAME = "Juan";
		String LAST_NAME = "Perez";
		String ADDRESS = "Av. Ejercito 123";
		String CITY = "Arequipa";
		String TELEPHONE = "987654321";

		Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
		Owner ownerCreated = ownerService.create(owner);

		assertNotNull(ownerCreated.getId());
		assertEquals(FIRST_NAME, ownerCreated.getFirstName());
		assertEquals(LAST_NAME, ownerCreated.getLastName());
		assertEquals(ADDRESS, ownerCreated.getAddress());
		assertEquals(CITY, ownerCreated.getCity());
		assertEquals(TELEPHONE, ownerCreated.getTelephone());
	}

	// 3. Prueba de Actualización (Update)
	@Test
	public void testUpdateOwner() {
		String FIRST_NAME = "George";
		String LAST_NAME = "Franklin";
		String UP_LAST_NAME = "FranklinUpdated";
		String ADDRESS = "110 W. Liberty St.";
		String CITY = "Madison";
		String TELEPHONE = "6085551023";

		Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
		Owner ownerCreated = ownerService.create(owner);

		ownerCreated.setLastName(UP_LAST_NAME);
		Owner ownerUpdated = ownerService.update(ownerCreated);

		assertEquals(UP_LAST_NAME, ownerUpdated.getLastName());
	}

	// 4. Prueba de Eliminación (Delete)
	@Test
	public void testDeleteOwner() {
		String FIRST_NAME = "Pedro";
		String LAST_NAME = "Gomez";
		String ADDRESS = "Calle Real 456";
		String CITY = "Lima";
		String TELEPHONE = "912345678";

		Owner owner = new Owner(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);
		Owner ownerCreated = ownerService.create(owner);

		try {
			ownerService.delete(ownerCreated.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		assertThrows(OwnerNotFoundException.class, () -> {
			ownerService.findById(ownerCreated.getId());
		});
	}
}