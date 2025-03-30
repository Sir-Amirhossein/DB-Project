package org.example.presenters.commands.chat;

import org.example.models.requests.Chat;
import org.example.presenters.services.ChatService;

public class UpdateChatCommand implements ChatCommand {

    private final Chat chat;
    private final ChatService service;

    public UpdateChatCommand(Chat chat, ChatService service) {
        this.chat = chat;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.updateChat(chat);
    }
}
