package com.hustcinema.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.dto.request.holdSeatRequest;
import com.hustcinema.backend.dto.respond.HoldSeatRespond;
import com.hustcinema.backend.model.HoldSeat;
import com.hustcinema.backend.service.HoldSeatService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/holdseat")
public class HoldSeatController {

    @Autowired
    private HoldSeatService holdSeatService;
    @PostMapping("/hold")
    public HoldSeat holdingseat(@RequestBody holdSeatRequest request) {
        return holdSeatService.holdingSeat(request.getSeatId(), request.getScheduleId());
    }

    @PostMapping("/free")
    public void freeSeat(@RequestBody holdSeatRequest request) {
        holdSeatService.freeSeat(request.getSeatId(), request.getScheduleId());
    }

    @GetMapping("/")
    public List<HoldSeatRespond> findHoddingSeat() {
        return holdSeatService.findHoddingSeat();
    }
    
}
