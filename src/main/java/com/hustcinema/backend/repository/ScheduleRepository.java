package com.hustcinema.backend.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hustcinema.backend.model.Schedule;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    boolean existsByDateAndShowTimeAndRoomId(LocalDate date, LocalTime showTime, String roomId);

    List<Schedule> findByDate(LocalDate date);

    List<Schedule> findByMovieIdAndDate(String movieId, LocalDate date);

    List<Schedule> findByMovieId(String movieId);

    List<Schedule> findByRoomId(String roomId);

    Schedule findByMovieIdAndDateAndShowTime(String movieId, LocalDate date, LocalTime showTime);

    // List<Schedule> deleteAllByDate(LocalDate date);
} 
