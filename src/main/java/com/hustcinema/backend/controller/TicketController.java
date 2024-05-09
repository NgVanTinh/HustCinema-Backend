package com.hustcinema.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.dto.respond.TicketRespond;
import com.hustcinema.backend.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    
    @GetMapping("/{userId}")
    List<TicketRespond> getAllTicketByUserId(@PathVariable String userId){
        return ticketService.findTicketByUserId(userId);
    }

    @GetMapping("/my-ticket")
    List<TicketRespond> getAllTicketByUserId(){
        return ticketService.findMyTicket();
    }
}
