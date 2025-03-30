package org.example.presenters.services;

import org.example.models.requests.Message;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public ResponseCodes addMessage (Message message) throws SQLException {
        messageRepository.addMessage(message);
        return ResponseCodes.SUCCESS;
    }

    public Message getMessage (int id) throws SQLException {
        return this.messageRepository.getMessage(id);
    }

    public ResponseCodes updateMessage (Message message) throws SQLException {
        return this.messageRepository.updateMessage(message);
    }

    public ResponseCodes deleteMessage (int id) throws SQLException {
        return this.messageRepository.deleteMessage(id);
    }
}
