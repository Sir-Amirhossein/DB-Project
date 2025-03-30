package org.example.presenters.controllers.invokers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.members.GroupMembersCommand;
import java.time.LocalDateTime;

public class GroupMembersInvoker {
    private final Logger logger = LogManager.getLogger(GroupMembersInvoker.class);
    private static GroupMembersInvoker groupMembersInvoker;

    public static GroupMembersInvoker getInstance() {
        if (groupMembersInvoker == null) {
            groupMembersInvoker = new GroupMembersInvoker();
        }
        return groupMembersInvoker;
    }

    public Object doCommand(GroupMembersCommand command) {
        addLog(command);
        try {
            return command.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseCodes.INTERNAL_SERVER_ERROR;
        }
    }
    private void addLog(GroupMembersCommand command) {
        this.logger.info("Command with name {} requested at {} \n",
                command.getClass().getSimpleName(), LocalDateTime.now());
    }
}
