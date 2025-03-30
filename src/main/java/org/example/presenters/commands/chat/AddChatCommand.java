package org.example.presenters.commands.chat;

import org.example.models.requests.Chat;
import org.example.presenters.commands.chat.ChatCommand;
import org.example.presenters.services.ChatService;

public class AddChatCommand implements ChatCommand {
    private final Chat chat;
    private final ChatService service;

    public AddChatCommand(Chat chat, ChatService service) {
        this.chat = chat;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.addChat(this.chat);
    }
}
