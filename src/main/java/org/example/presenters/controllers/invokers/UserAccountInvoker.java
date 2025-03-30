package org.example.presenters.controllers.invokers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.user.UserCommand;
import java.time.LocalDateTime;

public class UserAccountInvoker {
    private final Logger logger = LogManager.getLogger(UserAccountInvoker.class);
    private static UserAccountInvoker authorInvoker;

    public static UserAccountInvoker getInstance() {
        if (authorInvoker == null) {
            authorInvoker = new UserAccountInvoker();
        }
        return authorInvoker;
    }

    public Object doCommand(UserCommand command) {
        addLog(command);
        try {
            return command.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseCodes.INTERNAL_SERVER_ERROR;
        }
    }
    private void addLog(UserCommand command) {
        this.logger.info("Command with name {} requested at {} \n",
                command.getClass().getSimpleName(), LocalDateTime.now());
    }
}
