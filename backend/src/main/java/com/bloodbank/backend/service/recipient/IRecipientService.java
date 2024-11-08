package com.bloodbank.backend.service.recipient;

import com.bloodbank.backend.dto.RecipientDto;
import com.bloodbank.backend.model.Person;
import com.bloodbank.backend.model.Recipient;

import java.util.List;

public interface IRecipientService {
    Recipient getRecipientByPersonId(Long personId);

    Recipient createRecipient(Long personId);

    List<Recipient> getAllRecipients();
    List<Recipient> getAllRecipientsByBloodType(String bloodType);

    RecipientDto convertToDto(Recipient recipient);
}
