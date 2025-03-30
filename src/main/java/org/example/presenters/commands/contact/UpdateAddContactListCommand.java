package org.example.presenters.commands.contact;

import org.example.models.requests.ContactList;
import org.example.presenters.services.ContactListService;

public class UpdateAddContactListCommand implements ContactListCommand {

    private final ContactList contactList;
    private final ContactListService service;

    public UpdateAddContactListCommand(ContactList contactList, ContactListService service) {
        this.contactList = contactList;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.updateContact(contactList);
    }
}
