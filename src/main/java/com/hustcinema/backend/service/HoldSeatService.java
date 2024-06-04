package com.hustcinema.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.dto.respond.HoldSeatRespond;
import com.hustcinema.backend.model.HoldSeat;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.model.Seat;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.SeatRepository;
import com.hustcinema.backend.repository.UserRepository;
import com.hustcinema.backend.repository.HoldSeatRepository;
import com.hustcinema.backend.repository.ScheduleRepository;

@Service
public class HoldSeatService {
    
    @Autowired
    private SeatRepository seatRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private HoldSeatRepository holdSeatRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    public HoldSeat holdingSeat(String seatId, String scheduleId){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        // System.out.println("hold service - username: " + name);
        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("schedule not found"));
        HoldSeat holdSeat = new HoldSeat();
        holdSeat.setSeat(seat);
        holdSeat.setSchedule(schedule);
        holdSeat.setUser(user);
        return holdSeatRepository.save(holdSeat);
    }

    public String freeAllSeatByUserIdAndScheduleId(String scheduleId){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        // System.out.println("free all seat service - username: " + name);
        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("schedule not found"));
        List<HoldSeat> holdSeat = holdSeatRepository.findByUserAndSchedule(user, schedule);
        for(HoldSeat seat : holdSeat){
            holdSeatRepository.deleteById(seat.getId());
        }
        return "free all successfully";
    }

    public void freeSeat(String seatId, String scheduleId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("schedule not found"));
        HoldSeat holdSeat = holdSeatRepository.findBySeatAndSchedule(seat, schedule)
                .orElseThrow(() -> new RuntimeException("holdSeat not found"));
        holdSeatRepository.deleteById(holdSeat.getId());
    }

    public List<HoldSeatRespond>findHoddingSeat(){
        List<HoldSeat> holdingSeats = holdSeatRepository.findAll();
        List<HoldSeatRespond> holdSeatResponds = new ArrayList<HoldSeatRespond>();
        for(HoldSeat holdingSeat : holdingSeats){
            HoldSeatRespond holdSeatRespond = new HoldSeatRespond();
            holdSeatRespond.setSeatId(holdingSeat.getSeat().getId());
            holdSeatRespond.setScheduleId(holdingSeat.getSchedule().getId());
            holdSeatRespond.setUserId(holdingSeat.getUser().getId());
            holdSeatResponds.add(holdSeatRespond);
        }
        
        return holdSeatResponds;
    }


}
