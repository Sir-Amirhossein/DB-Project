package org.example.presenters.commands.chat;

import org.example.presenters.services.ChatService;

public class DeleteChatCommand implements ChatCommand {

    private final int id;
    private final ChatService service;

    public DeleteChatCommand(int id, ChatService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.deleteChat(id);
    }
}
