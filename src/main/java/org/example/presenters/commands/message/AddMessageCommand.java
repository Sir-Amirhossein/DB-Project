package org.example.presenters.commands.message;

import org.example.models.requests.Message;
import org.example.presenters.commands.message.MessageCommand;
import org.example.presenters.services.MessageService;

public class AddMessageCommand implements MessageCommand {
    private final Message message;
    private final MessageService service;

    public AddMessageCommand(Message message, MessageService service) {
        this.message = message;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return service.addMessage(message);
    }
}
