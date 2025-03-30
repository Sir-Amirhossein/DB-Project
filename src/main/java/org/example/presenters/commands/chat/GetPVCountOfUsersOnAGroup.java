package org.example.presenters.commands.chat;

import org.example.presenters.services.ChatService;

public class GetPVCountOfUsersOnAGroup implements ChatCommand {

    private final int id;
    private final ChatService service;

    public GetPVCountOfUsersOnAGroup(int id, ChatService service) {
        this.id = id;
        this.service = service;
    }

    @Override
    public Object execute() throws Exception {
        return this.service.getPVUsersOnAGroup(id);
    }
}
