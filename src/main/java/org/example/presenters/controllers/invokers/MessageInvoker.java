package org.example.presenters.controllers.invokers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.message.MessageCommand;

import java.time.LocalDateTime;

public class MessageInvoker {
    private final Logger logger = LogManager.getLogger(MessageInvoker.class);
    private static MessageInvoker messageInvoker;

    public static MessageInvoker getInstance() {
        if (messageInvoker == null) {
            messageInvoker = new MessageInvoker();
        }
        return messageInvoker;
    }

    public Object doCommand(MessageCommand command) {
        addLog(command);
        try {
            return command.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseCodes.INTERNAL_SERVER_ERROR;
        }
    }
    private void addLog(MessageCommand command) {
        this.logger.info("Command with name {} requested at {} \n",
                command.getClass().getSimpleName(), LocalDateTime.now());
    }

}
