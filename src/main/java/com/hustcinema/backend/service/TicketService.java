package com.hustcinema.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.dto.respond.TicketRespond;
import com.hustcinema.backend.model.Bill;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.model.Ticket;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.BillRepository;
import com.hustcinema.backend.repository.ScheduleRepository;
import com.hustcinema.backend.repository.TicketRepository;
import com.hustcinema.backend.repository.UserRepository;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;
    
    // @PreAuthorize("hasRole('ADMIN')")
    public List<TicketRespond> findTicketByUserId(String userId) {
        List<Ticket> listTicket = ticketRepository.findByUserId(userId);
       
        List<TicketRespond> listRespond = new ArrayList<TicketRespond>();
        for (Ticket ticket : listTicket) {
            Schedule schedule = scheduleRepository.findById(ticket.getSchedule().getId())
                    .orElseThrow( () -> new RuntimeException("Schedule not found"));
            Bill bill = billRepository.findById(ticket.getBill().getId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            TicketRespond respond = new TicketRespond();
            respond.setTicketId(ticket.getId());
            respond.setCreatedTime(bill.getCreatedTime());
            respond.setSeatName(ticket.getSeat().getSeatName());
            respond.setDate(schedule.getDate());
            respond.setShowTime(schedule.getShowTime());
            respond.setRoomName(schedule.getRoom().getRoomName());

            listRespond.add(respond);
        }
        return listRespond;
    } 

    public List<TicketRespond> findMyTicket() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        List<Ticket> listTicket = ticketRepository.findByUserId(user.getId());

        List<TicketRespond> listRespond = new ArrayList<TicketRespond>();
        for (Ticket ticket : listTicket) {
            Schedule schedule = scheduleRepository.findById(ticket.getSchedule().getId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            Bill bill = billRepository.findById(ticket.getBill().getId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            TicketRespond respond = new TicketRespond();
            respond.setTicketId(ticket.getId());
            respond.setCreatedTime(bill.getCreatedTime());
            respond.setSeatName(ticket.getSeat().getSeatName());
            respond.setDate(schedule.getDate());
            respond.setShowTime(schedule.getShowTime());
            respond.setRoomName(schedule.getRoom().getRoomName());

            listRespond.add(respond);
        }
        return listRespond;
    }
}
