package org.example.presenters.controllers.invokers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.chat.ChatCommand;

import java.time.LocalDateTime;

public class ChatInvoker {
    private final Logger logger = LogManager.getLogger(ChatInvoker.class);
    private static ChatInvoker chatInvoker;

    public static ChatInvoker getInstance() {
        if (chatInvoker == null) {
            chatInvoker = new ChatInvoker();
        }
        return chatInvoker;
    }

    public Object doCommand(ChatCommand command) {
        addLog(command);
        try {
            return command.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseCodes.INTERNAL_SERVER_ERROR;
        }
    }
    private void addLog(ChatCommand command) {
        this.logger.info("Command with name {} requested at {} \n",
                command.getClass().getSimpleName(), LocalDateTime.now());
    }
}
