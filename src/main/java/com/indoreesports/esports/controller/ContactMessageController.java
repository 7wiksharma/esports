package com.indoreesports.esports.controller;

import com.indoreesports.esports.entity.ContactMessage;
import com.indoreesports.esports.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactMessageController {
    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping
    public ContactMessage createMessage(@RequestBody ContactMessage message) {
        return contactMessageService.createMessage(message);
    }

    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return contactMessageService.getAllMessages();
    }
}
