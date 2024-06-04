package com.hustcinema.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.dto.respond.HoldSeatRespond;
import com.hustcinema.backend.dto.respond.SeatRespond;
import com.hustcinema.backend.exception.SeatExistException;
import com.hustcinema.backend.model.HoldSeat;
import com.hustcinema.backend.model.Room;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.model.Seat;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.RoomRepository;
import com.hustcinema.backend.repository.ScheduleRepository;
import com.hustcinema.backend.repository.SeatRepository;
import com.hustcinema.backend.repository.TicketRepository;
import com.hustcinema.backend.repository.UserRepository;

@Service
public class SeatService {
    
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private HoldSeatService holdSeatService;

    @Autowired
    private UserRepository userRepository;
    // @PreAuthorize("hasRole('ADMIN')")
    public Seat createNewSeat(String roomId, String seatName){
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("room not found with id " + roomId));
        if (seatRepository.existsByRoomAndSeatName(room, seatName)) {
            throw new SeatExistException(room, seatName);
        }
        
        Seat seat = new Seat();
        seat.setSeatName(seatName);
        seat.setRoom(room);  
        return seatRepository.save(seat);
    }

    // láy ra danh sách ghế theo lịch chiếu và trạng thái mỗi ghế
    public List<SeatRespond> getSeatsByScheduleId(String scheduleId) {
        // Lấy thông tin user hiện tại
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        // Lấy ra các chỗ ngồi của phòng trong lịch chiếu
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
        Room room = schedule.getRoom();
        List<Seat> listSeat = seatRepository.findByRoomId(room.getId());

        // Lấy các chỗ ngồi đã được đặt từ danh sách các vé trong lịch chiếu
        List<Seat> occupiedSeats = ticketRepository.findByScheduleId(scheduleId)
                .stream().map(ticket -> ticket.getSeat())
                .collect(Collectors.toList());
        // Lấy các chỗ ngồi đang được giữ từ bảng holdseat
        List<HoldSeatRespond> holdSeatList = holdSeatService.findHoddingSeat();
        
        // Map danh sách seat sang danh sách SeatRespond đã có ghi chú các chỗ ngồi hiện đã bị đặt
        List<SeatRespond> seatRequestDTOs = new ArrayList<>();
        for (Seat seat : listSeat) {
            SeatRespond seatRequestDTO = new SeatRespond();
            seatRequestDTO.setSeatId(seat.getId());
            seatRequestDTO.setSeatName(seat.getSeatName());
            seatRequestDTO.setRoomId(seat.getRoom().getId());

            // Kiểm tra xem chỗ ngồi có được đặt hay không
            if (occupiedSeats.contains(seat)) {
                seatRequestDTO.setIsOccupied(true);
            } 
            else {
                //Kiểm tra ghế ngồi có đang bị giữ không
                for(HoldSeatRespond holdseat: holdSeatList){
                    if((holdseat.getUserId() != user.getId()) && (holdseat.getSeatId() == seat.getId())){
                        seatRequestDTO.setHold(true);
                    }
                }
                seatRequestDTO.setIsOccupied(false);
            }
           
            seatRequestDTOs.add(seatRequestDTO);
            
        }
        return seatRequestDTOs;
    }

    
}
