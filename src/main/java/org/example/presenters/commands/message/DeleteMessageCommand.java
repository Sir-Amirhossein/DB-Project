package org.example.presenters.commands.message;

import org.example.presenters.services.MessageService;

public class DeleteMessageCommand implements MessageCommand {

    private final int id;
    private final MessageService service;

    public DeleteMessageCommand(int id, MessageService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.deleteMessage(id);
    }
}
