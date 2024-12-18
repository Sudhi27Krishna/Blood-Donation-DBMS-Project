package com.bloodbank.backend.controller;

import com.bloodbank.backend.dto.PersonDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.request.CreatePersonRequest;
import com.bloodbank.backend.request.UpdatePersonRequest;
import com.bloodbank.backend.response.ApiResponse;
import com.bloodbank.backend.service.person.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {
    private final IPersonService personService;

    @GetMapping("/{id}/person")
    public ResponseEntity<ApiResponse> getPersonById(@PathVariable Long id){
        try {
            Person person = personService.getPersonById(id);
            PersonDto personDto = personService.convertToDto(person);
            return ResponseEntity.ok(new ApiResponse("Person found!", personDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}/person-by-userId")
    public ResponseEntity<ApiResponse> getPersonByUserId(@PathVariable Long userId){
        try {
            Person person = personService.getPersonByUserId(userId);
            PersonDto personDto = personService.convertToDto(person);
            return ResponseEntity.ok(new ApiResponse("Person found!", personDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPersons(){
        List<Person> personList = personService.getAllPersons();
        List<PersonDto> personDtoList = personList.stream().map(personService::convertToDto).toList();
        return ResponseEntity.ok(new ApiResponse("Success", personDtoList));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPerson(@RequestBody CreatePersonRequest request){
        try {
            Person person = personService.createPerson(request);
            PersonDto personDto = personService.convertToDto(person);
            return ResponseEntity.ok(new ApiResponse("Create person success!", personDto));
        } catch (AlreadyExistsException | ResourceNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updatePerson(@PathVariable Long id, @RequestBody UpdatePersonRequest request){
        try {
            Person udpdatedPerson = personService.updatePerson(id, request);
            PersonDto personDto = personService.convertToDto(udpdatedPerson);
            return ResponseEntity.ok(new ApiResponse("Update success", personDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
