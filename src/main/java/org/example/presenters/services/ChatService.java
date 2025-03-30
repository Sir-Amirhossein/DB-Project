package org.example.presenters.services;

import org.example.models.requests.Chat;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public ResponseCodes addChat (Chat chat) throws SQLException {
        chatRepository.addChat(chat);
        return ResponseCodes.SUCCESS;
    }

    public Chat getChat (int id) throws SQLException {
        return this.chatRepository.getChat(id);
    }

    public ResponseCodes updateChat (Chat chat) throws SQLException {
        return this.chatRepository.updateChat(chat);
    }

    public ResponseCodes deleteChat (int id) throws SQLException {
        return this.chatRepository.deleteChat(id);
    }

    public int getPVUsersOnAGroup (int chat_id) throws SQLException {
        return this.chatRepository.getPVUsersOnAGroup(chat_id);
    }
}