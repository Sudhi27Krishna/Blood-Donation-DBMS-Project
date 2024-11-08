package com.bloodbank.backend.service.donor;

import com.bloodbank.backend.dto.DonorDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Donor;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.repository.DonorRepository;
import com.bloodbank.backend.service.person.IPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DonorService implements IDonorService {
    private final DonorRepository donorRepository;
    private final IPersonService personService;
    private final ModelMapper modelMapper;

    @Override
    public Donor getDonorByPersonId(Long personId){
        return Optional.ofNullable(donorRepository.findByPersonId(personId))
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found"));
    }

    @Override
    public Donor createDonor(Long personId) {
        return Optional.of(personId)
                .filter(pId -> !donorRepository.existsByPersonId(pId))
                .map(pId -> {
                    Donor donor = new Donor();
                    Person donorPerson = personService.getPersonById(pId);
                    donor.setPerson(donorPerson);
                    return donorRepository.save(donor);
                })
                .orElseThrow(() -> new AlreadyExistsException("Donor already exists!"));
    }

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public List<Donor> getAllDonorsByBloodType(String bloodType) {
        List<Person> personList = personService.getPersonByBloodType(bloodType);
        return personList.stream()
                .map(person -> donorRepository.findByPersonId(person.getId()))
                .toList();
    }

    @Override
    public DonorDto convertToDto(Donor donor) {
        DonorDto donorDto = modelMapper.map(donor, DonorDto.class);
        donorDto.setBloodType(personService.getPersonById(donor.getPerson().getId()).getBloodType());
        return donorDto;
    }
}
