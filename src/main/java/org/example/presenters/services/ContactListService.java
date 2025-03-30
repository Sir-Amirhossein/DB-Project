package org.example.presenters.services;

import org.example.models.requests.ContactList;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.repository.ContactListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class ContactListService {
    @Autowired
    ContactListRepository contactListRepository;
    public ResponseCodes addContactList (ContactList contactList) throws SQLException {
        contactListRepository.addContact(contactList);
        return ResponseCodes.SUCCESS;
    }

    public ContactList getContact (int id) throws SQLException {
        return this.contactListRepository.getContact(id);
    }

    public ResponseCodes updateContact (ContactList contactList) throws SQLException {
        return this.contactListRepository.updateContact(contactList);
    }

    public ResponseCodes deleteContact (int id) throws SQLException {
        return this.contactListRepository.deleteContact(id);
    }
}