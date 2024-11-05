package com.bloodbank.backend.service.person;

import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.repository.PersonRepository;
import com.bloodbank.backend.request.CreatePersonRequest;
import com.bloodbank.backend.request.UpdatePersonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {
    private final PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User does not exist!"));
    }

    @Override
    public Person createPerson(CreatePersonRequest request) {
        return Optional.of(request)
                .filter(person -> !personRepository.existsByEmail(person.email()))
                .map(req -> {
                    Person person = new Person();
                    person.setName(req.name());
                    person.setAge(req.age());
                    person.setGender(req.gender());
                    person.setEmail(req.email());
                    person.setBloodType(req.bloodType());
                    person.setAddress(req.address());
                    return personRepository.save(person);
                })
                .orElseThrow(() -> new AlreadyExistsException("User already exists!"));
    }

    @Override
    public Person updatePerson(Long id, UpdatePersonRequest personRequest) {
        return personRepository.findById(id).map(existingPerson -> {
            existingPerson.setName(personRequest.name());
            existingPerson.setAge(personRequest.age());
            existingPerson.setEmail(personRequest.email());
            existingPerson.setAddress(personRequest.address());
            return personRepository.save(existingPerson);
        }).orElseThrow(() -> new ResourceNotFoundException("User does not exist!"));
    }
}
