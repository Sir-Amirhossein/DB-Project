package org.example.presenters.controllers;

import org.example.models.requests.*;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.admin.AddAdminGroupCommand;
import org.example.presenters.commands.admin.AdminGroupCommand;
import org.example.presenters.commands.admin.SelectAdminGroupCommand;
import org.example.presenters.commands.chat.AddChatCommand;
import org.example.presenters.commands.chat.ChatCommand;
import org.example.presenters.commands.chat.SelectChatCommand;
import org.example.presenters.commands.contact.AddContactListCommand;
import org.example.presenters.commands.contact.ContactListCommand;
import org.example.presenters.commands.contact.SelectContactListCommand;
import org.example.presenters.commands.members.AddGroupMembersCommand;
import org.example.presenters.commands.members.GroupMembersCommand;
import org.example.presenters.commands.members.SelectGroupMembersCommand;
import org.example.presenters.commands.message.AddMessageCommand;
import org.example.presenters.commands.message.MessageCommand;
import org.example.presenters.commands.message.SelectMessageCommand;
import org.example.presenters.commands.user.AddUserAccountCommand;
import org.example.presenters.commands.user.SelectUserAccountCommand;
import org.example.presenters.commands.user.UserCommand;
import org.example.presenters.controllers.invokers.*;
import org.example.presenters.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messenger/select")
public class SelectController {

    private final UserAccountInvoker userAccountInvoker = UserAccountInvoker.getInstance();
    private final MessageInvoker messageInvoker = MessageInvoker.getInstance();
    private final ChatInvoker chatInvoker = ChatInvoker.getInstance();
    private final GroupMembersInvoker groupMembersInvoker = GroupMembersInvoker.getInstance();
    private final AdminGroupInvoker adminGroupInvoker = AdminGroupInvoker.getInstance();
    private final ContactListInvoker contactListInvoker = ContactListInvoker.getInstance();
    private final UserAccountService userAccountService;
    private final MessageService messageService;
    private final ChatService chatService;
    private final GroupMembersService groupMembersService;
    private final AdminGroupService adminGroupService;
    private final ContactListService contactListService;

    @Autowired
    public SelectController(UserAccountService userAccountService, MessageService messageService,
                            ChatService chatService, GroupMembersService groupMembersService,
                            AdminGroupService adminGroupService, ContactListService contactListService) {
        this.userAccountService = userAccountService;
        this.messageService = messageService;
        this.chatService = chatService;
        this.groupMembersService = groupMembersService;
        this.adminGroupService = adminGroupService;
        this.contactListService = contactListService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userAccount/{id}")
    public ResponseEntity<Object> selectUserAccount(@PathVariable(value = "id") int id) {
        UserCommand command = new SelectUserAccountCommand(id, this.userAccountService);
        UserAccount response = (UserAccount) userAccountInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/message/{id}")
    public ResponseEntity<Object> selectMessage(@PathVariable(value = "id") int id) {
        MessageCommand command = new SelectMessageCommand(id, messageService);
        Message response = (Message) messageInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chat/{id}")
    public ResponseEntity<Object> selectChat(@PathVariable(value = "id") int id) {
        ChatCommand command = new SelectChatCommand(id, chatService);
        Chat response = (Chat) chatInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/members/{id}")
    public ResponseEntity<Object> selectMember(@PathVariable(value = "id") int id) {
        GroupMembersCommand command = new SelectGroupMembersCommand(id, groupMembersService);
        GroupMembers response = (GroupMembers) groupMembersInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/{id}")
    public ResponseEntity<Object> selectAdminGroup(@PathVariable(value = "id") int id) {
        AdminGroupCommand command = new SelectAdminGroupCommand(id, adminGroupService);
        AdminGroup response = (AdminGroup) adminGroupInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contact/{id}")
    public ResponseEntity<Object> selectContact(@PathVariable(value = "id") int id) {
        ContactListCommand command = new SelectContactListCommand(id, contactListService);
        ContactList response = (ContactList) contactListInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }
}