package org.example.presenters.controllers;

import org.example.models.requests.Chat;
import org.example.models.requests.UserAccount;
import org.example.models.responses.UserMessageCount;
import org.example.presenters.commands.chat.ChatCommand;
import org.example.presenters.commands.chat.GetPVCountOfUsersOnAGroup;
import org.example.presenters.commands.user.GetUsersByMessageCountCommand;
import org.example.presenters.commands.user.GetUsersInCommonGroupsCommand;
import org.example.presenters.commands.user.UserCommand;
import org.example.presenters.commands.user.UserGroupCommand;
import org.example.presenters.controllers.invokers.*;
import org.example.presenters.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messenger/report")
public class ReportsController {
    private final UserAccountInvoker userAccountInvoker = UserAccountInvoker.getInstance();
    private final ChatInvoker chatInvoker = ChatInvoker.getInstance();
    private final UserAccountService userAccountService;
    private final ChatService chatService;

    @Autowired
    public ReportsController(UserAccountService userAccountService, ChatService chatService) {
        this.userAccountService = userAccountService;
        this.chatService = chatService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userGroups")
    public ResponseEntity<Object> getUserGroups(@RequestBody UserAccount userAccount) {
        UserCommand command = new UserGroupCommand(userAccount, userAccountService);
        List<Chat> response = (List<Chat>) userAccountInvoker.doCommand(command);
        if (response.isEmpty())
            return new ResponseEntity<>("User with username = " + userAccount.getUserName() +
                    "is not in any groups.", HttpStatusCode.valueOf(200));
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/count_of_PV_users_on_group/{chat_id}")
    public ResponseEntity<Object> getPVUsersOnAGroup (@PathVariable("chat_id") int id) {
        ChatCommand command = new GetPVCountOfUsersOnAGroup(id, chatService);
        int count = (int) chatInvoker.doCommand(command);
        return new ResponseEntity<>(count, HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users_in_common_groups/{id}")
    public ResponseEntity<Object> getUsersInCommonGroups (@PathVariable("id") int id) {
        UserCommand command = new GetUsersInCommonGroupsCommand(id, userAccountService);
        List<UserAccount> response = (List<UserAccount>) userAccountInvoker.doCommand(command);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_users_by_message_counts/{chat_id}")
    public ResponseEntity<Object> getUsersByMessageCount (@PathVariable("chat_id") int id) {
        UserCommand command = new GetUsersByMessageCountCommand(id, userAccountService);
        List<UserMessageCount> response = (List<UserMessageCount>) userAccountInvoker.doCommand(command);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }
}
