package org.example.presenters.controllers.invokers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.admin.AdminGroupCommand;
import java.time.LocalDateTime;

public class AdminGroupInvoker {
    private final Logger logger = LogManager.getLogger(AdminGroupInvoker.class);
    private static AdminGroupInvoker adminGroupInvoker;

    public static AdminGroupInvoker getInstance() {
        if (adminGroupInvoker == null) {
            adminGroupInvoker = new AdminGroupInvoker();
        }
        return adminGroupInvoker;
    }

    public Object doCommand(AdminGroupCommand command) {
        addLog(command);
        try {
            return command.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseCodes.INTERNAL_SERVER_ERROR;
        }
    }
    private void addLog(AdminGroupCommand command) {
        this.logger.info("Command with name {} requested at {} \n",
                command.getClass().getSimpleName(), LocalDateTime.now());
    }
}
