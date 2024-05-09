package com.hustcinema.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hustcinema.backend.dto.request.ScheduleRequest;
import com.hustcinema.backend.dto.respond.ScheduleRespond;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.service.ScheduleService;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin("*")
@RequestMapping("/api/schedule")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/")
    Schedule newSchedule(@RequestBody ScheduleRequest scheduleRequestDTO){
        return scheduleService.createNewSchedule(scheduleRequestDTO.getMovieId(), scheduleRequestDTO.getRoomId(), 
                                            scheduleRequestDTO.getDate(), scheduleRequestDTO.getShowTime(), scheduleRequestDTO.getPrice());
    }

    @GetMapping("/")
    List<ScheduleRespond> getAllSchedule(){
        return scheduleService.findAllSchedule();
    }

    @GetMapping("/{movieId}")
    List<ScheduleRespond> getScheduleByMovieId(@PathVariable String movieId) {
        return scheduleService.findScheduleByMovieId(movieId);
    }

    @PutMapping("/{id}")
    Schedule updateSchedule(@RequestBody ScheduleRequest scheduleRequestDTO ,@PathVariable String id){
        return scheduleService.updateSchedule(scheduleRequestDTO, id);
    }
    
    @DeleteMapping("/{id}")
    String deleteSchedule(@PathVariable String id) { 
        return scheduleService.deleteScheduleById(id);
    }
    
}
