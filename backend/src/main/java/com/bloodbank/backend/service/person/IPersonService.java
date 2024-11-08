package com.bloodbank.backend.service.person;

import com.bloodbank.backend.dto.PersonDto;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.request.CreatePersonRequest;
import com.bloodbank.backend.request.UpdatePersonRequest;

import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long id);
    List<Person> getPersonByBloodType(String bloodType);
    Person createPerson(CreatePersonRequest request);

    PersonDto convertToDto(Person person);
    Person updatePerson(Long id, UpdatePersonRequest personRequest);
}
