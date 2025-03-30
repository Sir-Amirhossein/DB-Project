package org.example.presenters.controllers.invokers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.contact.ContactListCommand;
import java.time.LocalDateTime;

public class ContactListInvoker {
    private final Logger logger = LogManager.getLogger(ContactListInvoker.class);
    private static ContactListInvoker contactListInvoker;

    public static ContactListInvoker getInstance() {
        if (contactListInvoker == null) {
            contactListInvoker = new ContactListInvoker();
        }
        return contactListInvoker;
    }

    public Object doCommand(ContactListCommand command) {
        addLog(command);
        try {
            return command.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseCodes.INTERNAL_SERVER_ERROR;
        }
    }
    private void addLog(ContactListCommand command) {
        this.logger.info("Command with name {} requested at {} \n",
                command.getClass().getSimpleName(), LocalDateTime.now());
    }
}
