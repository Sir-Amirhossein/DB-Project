package org.example.presenters.commands.chat;

import org.example.presenters.services.ChatService;

public class SelectChatCommand implements ChatCommand {

    private final int id;
    private final ChatService service;

    public SelectChatCommand(int id, ChatService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.getChat(id);
    }
}
