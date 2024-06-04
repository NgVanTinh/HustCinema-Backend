package com.hustcinema.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.dto.request.SeatRequest;
import com.hustcinema.backend.dto.request.holdSeatRequest;
import com.hustcinema.backend.dto.respond.SeatRespond;
import com.hustcinema.backend.model.Seat;
import com.hustcinema.backend.repository.SeatRepository;
import com.hustcinema.backend.service.SeatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/seat")

public class SeatController {
    
    @Autowired
    SeatService seatService;

    @Autowired
    SeatRepository seatRepository;
    @PostMapping("/")
    public Seat createNewSeat(@RequestBody SeatRequest seatRequestDTO ) {
        return seatService.createNewSeat(seatRequestDTO.getRoomId(), seatRequestDTO.getSeatName());
    }

    @GetMapping("/")
    public List<Seat> getAllSeat() {
        return seatRepository.findAll();
    }
    
    @GetMapping("/{scheduleId}")
    public List<SeatRespond> getSeatByScheduleId( @PathVariable String scheduleId, HttpServletRequest request) throws IOException{

        HttpSession session = (HttpSession) request.getSession();
        // Lưu id lịch chiếu
        session.setAttribute("scheduleId", scheduleId);
        // System.out.println("getSeat: scheduleId: " + session.getAttribute("scheduleId"));
        // Lấy danh sách ghế cho lịch chiếu đã chọn
        List<SeatRespond> listSeat = seatService.getSeatsByScheduleId(scheduleId);

        return listSeat;
    }
    
}
