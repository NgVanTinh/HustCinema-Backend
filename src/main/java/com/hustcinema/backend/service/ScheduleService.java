package com.hustcinema.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.dto.request.ScheduleRequest;
import com.hustcinema.backend.dto.respond.ScheduleRespond;
import com.hustcinema.backend.exception.ScheduleExistException;
import com.hustcinema.backend.model.Movie;
import com.hustcinema.backend.model.Room;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.repository.MovieRepository;
import com.hustcinema.backend.repository.RoomRepository;
import com.hustcinema.backend.repository.ScheduleRepository;
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    // @PreAuthorize("hasRole('ADMIN')")
    public Schedule createNewSchedule(String movieId, String roomId, LocalDate date, LocalTime showTime, Long price) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        if(scheduleRepository.existsByDateAndShowTimeAndRoomId(date, showTime, roomId)) {
            String roomName = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId))
                    .getRoomName();

            throw new ScheduleExistException(date, showTime, roomName);
        }
        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setRoom(room);
        schedule.setDate(date);
        schedule.setShowTime(showTime);
        schedule.setPrice(price);
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleRespond> findAllSchedule() {
        List<Schedule> temp = scheduleRepository.findAll();
        List<ScheduleRespond> scheduleRespond = new ArrayList<>(); // Tạo danh sách mới
        for (Schedule schedule : temp) {
            ScheduleRespond scheduleRespondTemp = new ScheduleRespond(); // Tạo đối tượng ScheduleRespond
            scheduleRespondTemp.setId(schedule.getId());
            scheduleRespondTemp.setMovieName(schedule.getMovie().getMovieName());
            scheduleRespondTemp.setRoomName(schedule.getRoom().getRoomName());
            scheduleRespondTemp.setDate(schedule.getDate());
            scheduleRespondTemp.setShowTime(schedule.getShowTime());
            scheduleRespondTemp.setPrice(schedule.getPrice());
            scheduleRespond.add(scheduleRespondTemp); // Thêm scheduleDTO vào danh sách scheduleRespond
        }
        return scheduleRespond;
    }

    public List<ScheduleRespond> findScheduleByMovieIdAndDate(String movieId, LocalDate date) {
        List<Schedule> temp = scheduleRepository.findByMovieIdAndDate(movieId, date );
        List<ScheduleRespond> scheduleRespond = new ArrayList<>(); // Tạo danh sách mới
        for (Schedule schedule : temp) {
            ScheduleRespond scheduleRespondTemp = new ScheduleRespond(); // Tạo đối tượng ScheduleRespond
            scheduleRespondTemp.setId(schedule.getId());
            scheduleRespondTemp.setMovieName(schedule.getMovie().getMovieName());
            scheduleRespondTemp.setRoomName(schedule.getRoom().getRoomName());
            scheduleRespondTemp.setDate(schedule.getDate());
            scheduleRespondTemp.setShowTime(schedule.getShowTime());
            scheduleRespondTemp.setPrice(schedule.getPrice());
            scheduleRespond.add(scheduleRespondTemp); // Thêm scheduleDTO vào danh sách scheduleRespond
        }
        return scheduleRespond;
    } 
    
    public List<ScheduleRespond> findScheduleByMovieId(String movieId) {
        List<Schedule> temp = scheduleRepository.findByMovieId(movieId);
        List<ScheduleRespond> scheduleRespond = new ArrayList<>(); // Tạo danh sách mới
        for (Schedule schedule : temp) {
            ScheduleRespond scheduleRespondTemp = new ScheduleRespond(); // Tạo đối tượng ScheduleRespond
            scheduleRespondTemp.setId(schedule.getId());
            scheduleRespondTemp.setMovieName(schedule.getMovie().getMovieName());
            scheduleRespondTemp.setRoomName(schedule.getRoom().getRoomName());
            scheduleRespondTemp.setDate(schedule.getDate());
            scheduleRespondTemp.setShowTime(schedule.getShowTime());
            scheduleRespondTemp.setPrice(schedule.getPrice());
            scheduleRespond.add(scheduleRespondTemp); // Thêm scheduleDTO vào danh sách scheduleRespond
        }
        return scheduleRespond;
    }

    public ScheduleRespond findScheduleById(String id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("No such schedule found for id"));
        
        ScheduleRespond scheduleRespondTemp = new ScheduleRespond(); // Tạo đối tượng ScheduleRespond
        scheduleRespondTemp.setId(schedule.getId());
        scheduleRespondTemp.setMovieName(schedule.getMovie().getMovieName());
        scheduleRespondTemp.setRoomName(schedule.getRoom().getRoomName());
        scheduleRespondTemp.setDate(schedule.getDate());
        scheduleRespondTemp.setShowTime(schedule.getShowTime());
        scheduleRespondTemp.setPrice(schedule.getPrice());
        
        return scheduleRespondTemp;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public Schedule updateSchedule(ScheduleRequest newSchedule, String scheduleId) {
        Schedule oldSchedule  = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new RuntimeException("Schedule not found"));
        Movie newMovie = movieRepository.findById(newSchedule.getMovieId())
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        Room newRoom = roomRepository.findById(newSchedule.getRoomId())
            .orElseThrow(() -> new RuntimeException("Room not found"));
        // Optional<Movie> movie = movieRepository.findById(newSchedule.getMovieId());
        oldSchedule.setMovie(newMovie);
        oldSchedule.setRoom(newRoom);
        oldSchedule.setDate(newSchedule.getDate());
        oldSchedule.setShowTime(newSchedule.getShowTime());
        oldSchedule.setPrice(newSchedule.getPrice());
        return scheduleRepository.save(oldSchedule); 
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public String deleteScheduleById(String id) {
        scheduleRepository.deleteById(id);
        return "Schedule with id = " + id + " has been deleted successfully";
    }

}
