package com.bloodbank.backend.service.person;

import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.request.CreatePersonRequest;
import com.bloodbank.backend.request.UpdatePersonRequest;

import java.util.List;

public interface IPersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long id);
    Person createPerson(CreatePersonRequest request);

    Person updatePerson(Long id, UpdatePersonRequest personRequest);
}
