package org.example.presenters.controllers;

import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.admin.AdminGroupCommand;
import org.example.presenters.commands.admin.DeleteAdminGroupCommand;
import org.example.presenters.commands.chat.ChatCommand;
import org.example.presenters.commands.chat.DeleteChatCommand;
import org.example.presenters.commands.contact.ContactListCommand;
import org.example.presenters.commands.contact.DeleteContactListCommand;
import org.example.presenters.commands.members.DeleteGroupMembersCommand;
import org.example.presenters.commands.members.GroupMembersCommand;
import org.example.presenters.commands.message.DeleteMessageCommand;
import org.example.presenters.commands.message.MessageCommand;
import org.example.presenters.commands.user.DeleteUserAccountCommand;
import org.example.presenters.commands.user.UserCommand;
import org.example.presenters.controllers.invokers.*;
import org.example.presenters.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messenger/delete")
public class DeleteController {

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
    public DeleteController(UserAccountService userAccountService, MessageService messageService,
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
    public ResponseEntity<Object> deleteUserAccount(@PathVariable(value = "id") int id) {
        UserCommand command = new DeleteUserAccountCommand(id, this.userAccountService);
        ResponseCodes response = (ResponseCodes) userAccountInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/message/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable(value = "id") int id) {
        MessageCommand command = new DeleteMessageCommand(id, messageService);
        ResponseCodes response = (ResponseCodes) messageInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chat/{id}")
    public ResponseEntity<Object> deleteChat(@PathVariable(value = "id") int id) {
        ChatCommand command = new DeleteChatCommand(id, chatService);
        ResponseCodes response = (ResponseCodes) chatInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/members/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable(value = "id") int id) {
        GroupMembersCommand command = new DeleteGroupMembersCommand(id, groupMembersService);
        ResponseCodes response = (ResponseCodes) groupMembersInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/{id}")
    public ResponseEntity<Object> deleteAdminGroup(@PathVariable(value = "id") int id) {
        AdminGroupCommand command = new DeleteAdminGroupCommand(id, adminGroupService);
        ResponseCodes response = (ResponseCodes) adminGroupInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contact/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable(value = "id") int id) {
        ContactListCommand command = new DeleteContactListCommand(id, contactListService);
        ResponseCodes response = (ResponseCodes) contactListInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }
}