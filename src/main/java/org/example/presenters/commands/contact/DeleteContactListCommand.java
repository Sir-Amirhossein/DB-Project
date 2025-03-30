package org.example.presenters.commands.contact;

import org.example.presenters.services.ContactListService;

public class DeleteContactListCommand implements ContactListCommand {

    private final int id;
    private final ContactListService service;

    public DeleteContactListCommand(int id, ContactListService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.deleteContact(id);
    }
}
