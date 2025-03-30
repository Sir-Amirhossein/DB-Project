package org.example.presenters.controllers;

import org.example.models.requests.*;
import org.example.models.responses.ResponseCodes;
import org.example.presenters.commands.admin.AdminGroupCommand;
import org.example.presenters.commands.admin.UpdateAdminGroupCommand;
import org.example.presenters.commands.chat.ChatCommand;
import org.example.presenters.commands.chat.UpdateChatCommand;
import org.example.presenters.commands.contact.ContactListCommand;
import org.example.presenters.commands.contact.UpdateAddContactListCommand;
import org.example.presenters.commands.members.GroupMembersCommand;
import org.example.presenters.commands.members.UpdateGroupMembers;
import org.example.presenters.commands.message.MessageCommand;
import org.example.presenters.commands.message.UpdateMessageCommand;
import org.example.presenters.commands.user.UpdateUserAccountCommand;
import org.example.presenters.commands.user.UserCommand;
import org.example.presenters.controllers.invokers.*;
import org.example.presenters.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messenger/update")
public class UpdateController {

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
    public UpdateController(UserAccountService userAccountService, MessageService messageService,
                            ChatService chatService, GroupMembersService groupMembersService,
                            AdminGroupService adminGroupService, ContactListService contactListService) {
        this.userAccountService = userAccountService;
        this.messageService = messageService;
        this.chatService = chatService;
        this.groupMembersService = groupMembersService;
        this.adminGroupService = adminGroupService;
        this.contactListService = contactListService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userAccount")
    public ResponseEntity<Object> updateUserAccount(@RequestBody UserAccount userAccount) {
        UserCommand command = new UpdateUserAccountCommand(userAccount, this.userAccountService);
        ResponseCodes response = (ResponseCodes) userAccountInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/message")
    public ResponseEntity<Object> updateMessage(@RequestBody Message message) {
        MessageCommand command = new UpdateMessageCommand(message, messageService);
        ResponseCodes response = (ResponseCodes) messageInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/chat")
    public ResponseEntity<Object> updateChat(@RequestBody Chat chat) {
        ChatCommand command = new UpdateChatCommand(chat, chatService);
        ResponseCodes response = (ResponseCodes) chatInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/members")
    public ResponseEntity<Object> updateMember(@RequestBody GroupMembers members) {
        GroupMembersCommand command = new UpdateGroupMembers(members, groupMembersService);
        ResponseCodes response = (ResponseCodes) groupMembersInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin")
    public ResponseEntity<Object> updateAdminGroup(@RequestBody AdminGroup adminGroup) {
        AdminGroupCommand command = new UpdateAdminGroupCommand(adminGroup, adminGroupService);
        ResponseCodes response = (ResponseCodes) adminGroupInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contact")
    public ResponseEntity<Object> updateContact(@RequestBody ContactList contactList) {
        ContactListCommand command = new UpdateAddContactListCommand(contactList, contactListService);
        ResponseCodes response = (ResponseCodes) contactListInvoker.doCommand(command);
        return new ResponseEntity<>(response.toString(), HttpStatusCode.valueOf(200));
    }
}
