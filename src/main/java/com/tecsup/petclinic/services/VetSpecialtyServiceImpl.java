package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;

    public VetSpecialtyServiceImpl(VetSpecialtyRepository vetSpecialtyRepository) {
        this.vetSpecialtyRepository = vetSpecialtyRepository;
    }

    @Override
    public VetSpecialty create(VetSpecialty vetSpecialty) {
        return vetSpecialtyRepository.save(vetSpecialty);
    }

    @Override
    public VetSpecialty update(VetSpecialty vetSpecialty) {
        return vetSpecialtyRepository.save(vetSpecialty);
    }

    @Override
    public void delete(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialty vetSpecialty = findById(vetId, specialtyId);
        vetSpecialtyRepository.delete(vetSpecialty);
    }

    @Override
    public VetSpecialty findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        Optional<VetSpecialty> vetSpecialty =
                vetSpecialtyRepository.findById(new VetSpecialtyId(vetId, specialtyId));
        if (!vetSpecialty.isPresent()) {
            throw new VetSpecialtyNotFoundException("VetSpecialty not found...!");
        }
        return vetSpecialty.get();
    }

    @Override
    public List<VetSpecialty> findByVetId(Integer vetId) {
        return vetSpecialtyRepository.findByVetId(vetId);
    }

    @Override
    public List<VetSpecialty> findBySpecialtyId(Integer specialtyId) {
        return vetSpecialtyRepository.findBySpecialtyId(specialtyId);
    }
}
