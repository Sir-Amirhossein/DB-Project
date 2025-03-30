package org.example.presenters.commands.message;

import org.example.models.requests.Message;
import org.example.presenters.services.MessageService;

public class UpdateMessageCommand implements MessageCommand {

    private final Message message;
    private final MessageService service;

    public UpdateMessageCommand(Message message, MessageService service) {
        this.message = message;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.updateMessage(message);
    }
}
