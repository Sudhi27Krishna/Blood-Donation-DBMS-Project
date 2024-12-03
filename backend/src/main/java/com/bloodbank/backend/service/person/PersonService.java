package com.bloodbank.backend.service.person;

import com.bloodbank.backend.dto.PersonDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.model.User;
import com.bloodbank.backend.repository.PersonRepository;
import com.bloodbank.backend.request.CreatePersonRequest;
import com.bloodbank.backend.request.UpdatePersonRequest;
import com.bloodbank.backend.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {
    private final PersonRepository personRepository;
    private final IUserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
    }

    @Override
    public List<Person> getPersonByBloodType(String bloodType) {
        return personRepository.findByBloodType(bloodType);
    }

    @Override
    public Person createPerson(CreatePersonRequest request) {
        return Optional.of(request)
                .filter(person -> !personRepository.existsByEmail(person.email()))
                .map(req -> {
                    User user = userService.getUserById(request.userId());

                    Person person = new Person();
                    person.setName(req.name());
                    person.setAge(req.age());
                    person.setGender(req.gender());
                    person.setEmail(req.email());
                    person.setBloodType(req.bloodType());
                    person.setAddress(req.address());
                    person.setUser(user);
                    return personRepository.save(person);
                })
                .orElseThrow(() -> new AlreadyExistsException("Person already exists!"));
    }

    @Override
    public PersonDto convertToDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public Person updatePerson(Long id, UpdatePersonRequest personRequest) {
        return personRepository.findById(id).map(existingPerson -> {
            existingPerson.setName(personRequest.name());
            existingPerson.setAge(personRequest.age());
            existingPerson.setEmail(personRequest.email());
            existingPerson.setAddress(personRequest.address());
            return personRepository.save(existingPerson);
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found!"));
    }
}
