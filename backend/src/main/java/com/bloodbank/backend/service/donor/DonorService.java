package com.bloodbank.backend.service.donor;

import com.bloodbank.backend.model.Donor;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.repository.DonorRepository;
import com.bloodbank.backend.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DonorService implements IDonorService {
    private final DonorRepository donorRepository;
    private final PersonRepository personRepository;

    @Override
    public Donor createDonor(Person person) {
        return Optional.ofNullable(donorRepository.findByPersonId(person.getId()))
                .orElseGet(() -> {
                    Donor donor = new Donor();
                    donor.setPerson(person);
                    return donorRepository.save(donor);
                });
    }

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public List<Donor> getAllDonorsByBloodType(String bloodType) {
        List<Person> personList = personRepository.findByBloodType(bloodType);
        return personList.stream()
                .map(person -> donorRepository.findByPersonId(person.getId()))
                .toList();
    }
}
