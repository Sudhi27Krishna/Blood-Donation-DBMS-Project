package com.bloodbank.backend.service.recipient;

import com.bloodbank.backend.dto.RecipientDto;
import com.bloodbank.backend.exception.AlreadyExistsException;
import com.bloodbank.backend.exception.ResourceNotFoundException;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.model.Recipient;
import com.bloodbank.backend.repository.RecipientRepository;
import com.bloodbank.backend.service.person.IPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipientService implements IRecipientService {
    private final RecipientRepository recipientRepository;
    private final IPersonService personService;
    private final ModelMapper modelMapper;

    @Override
    public Recipient getRecipientByPersonId(Long personId) {
        return Optional.ofNullable(recipientRepository.findByPersonId(personId))
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));
    }

    @Override
    public Recipient createRecipient(Long personId){
        return Optional.of(personId)
                .filter(pId -> !recipientRepository.existsByPersonId(pId))
                .map(pId -> {
                    Recipient recipient = new Recipient();
                    Person recipientPerson = personService.getPersonById(personId);
                    recipient.setPerson(recipientPerson);
                    return recipientRepository.save(recipient);
                })
                .orElseThrow(() -> new AlreadyExistsException("Recipient already exists!"));
    }

    @Override
    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    @Override
    public List<Recipient> getAllRecipientsByBloodType(String bloodType) {
        List<Person> personList = personService.getPersonByBloodType(bloodType);
        return personList.stream()
                .map(person -> recipientRepository.findByPersonId(person.getId()))
                .toList();
    }

    @Override
    public RecipientDto convertToDto(Recipient recipient){
        RecipientDto recipientDto = modelMapper.map(recipient, RecipientDto.class);
        recipientDto.setBloodType(personService.getPersonById(recipient.getPerson().getId()).getBloodType());
        return recipientDto;
    }
}
