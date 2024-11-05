package com.bloodbank.backend.service.recipient;

import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.model.Recipient;
import com.bloodbank.backend.repository.PersonRepository;
import com.bloodbank.backend.repository.RecipientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipientService implements IRecipientService {
    private final RecipientRepository recipientRepository;
    private final PersonRepository personRepository;
    @Override
    public Recipient createRecipient(Person person) {
        return Optional.ofNullable(recipientRepository.findByPersonId(person.getId()))
                .orElseGet(() -> {
                    Recipient recipient = new Recipient();
                    recipient.setPerson(person);
                    return recipientRepository.save(recipient);
                });
    }

    @Override
    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    @Override
    public List<Recipient> getAllRecipientsByBloodType(String bloodType) {
        List<Person> personList = personRepository.findByBloodType(bloodType);
        return personList.stream()
                .map(person -> recipientRepository.findByPersonId(person.getId()))
                .toList();
    }
}
