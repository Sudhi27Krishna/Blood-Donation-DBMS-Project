package com.bloodbank.backend.service.recipient;

import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.model.Recipient;

import java.util.List;

public interface IRecipientService {
    Recipient createRecipient(Person person);
    List<Recipient> getAllRecipients();
    List<Recipient> getAllRecipientsByBloodType(String bloodType);
}
