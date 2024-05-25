package com.hustcinema.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.dto.respond.BillRespond;
import com.hustcinema.backend.model.Bill;
import com.hustcinema.backend.model.Movie;
import com.hustcinema.backend.model.Room;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.model.Seat;
import com.hustcinema.backend.model.Ticket;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.BillRepository;
import com.hustcinema.backend.repository.MovieRepository;
import com.hustcinema.backend.repository.RoomRepository;
import com.hustcinema.backend.repository.ScheduleRepository;
import com.hustcinema.backend.repository.SeatRepository;
import com.hustcinema.backend.repository.TicketRepository;
import com.hustcinema.backend.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service

public class BillService {
    
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    
    public BillRespond makeNewBill(HttpSession session, String userId, List<String> listSelectedSeatId) {
        String scheduleId = (String) session.getAttribute("scheduleId");
        // String scheduleId =(String) session.getAttribute("scheduleId");
        // System.out.println("makeNewBill: get scheduleId: " + session.getAttribute("scheduleId"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found with id " + scheduleId));
        List<String> listSeatNames = new ArrayList<String>();
        List<Ticket> listTickets = new ArrayList<Ticket>();

        // Lưu user và time vào Bill 
        Bill newBill = new Bill();
        newBill.setUser(user);
        newBill.setCreatedTime(LocalDateTime.now());

        // tính tổng tiền
        newBill.setTotalPrice(schedule.getPrice() * listSelectedSeatId.size());
        session.setAttribute("totalPrice", newBill.getTotalPrice());
        // Lưu bill vào session
        session.setAttribute("newBill", newBill);
        // Với mỗi ghế ngồi check xem đã có ai đặt chưa, nếu rồi thì throw, roll back luôn còn 
        // Ngược lại thì đóng gói các thông tin ghế và lịch vào vé và lưu vào session
        listSelectedSeatId.forEach(seatId -> {
            // đóng gói schedule, seat và bill vào từng ticket rồi add vào list 
            Ticket ticket = new Ticket();
            ticket.setSchedule(schedule);
            Seat newSeat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));
            ticket.setSeat(newSeat);
            listSeatNames.add(newSeat.getSeatName());
            listTickets.add(ticket);
             
        });

        // lưu ticket vào session
        session.setAttribute("listTicket", listTickets);

        // Tạo respond
        BillRespond respond = new BillRespond();
        respond.setCreatedTime(newBill.getCreatedTime());
        respond.setUserName(user.getUserName());
        Movie movie = movieRepository.findById(schedule.getMovie().getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
        respond.setMovieName(movie.getMovieName());
        respond.setDate(schedule.getDate());
        respond.setShowTime(schedule.getShowTime());
        Room room = roomRepository.findById(schedule.getRoom().getId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));
        respond.setRoomName(room.getRoomName());
        respond.setListSeatName(listSeatNames);
        respond.setTotalPrice(newBill.getTotalPrice());
        respond.setStatus("Unpaid");

        session.setAttribute("respond", respond);
        // System.out.println("makeNewBill: get total: " + session.getAttribute("totalPrice"));
        return respond;
        
    }

    // @PreAuthorize("hasRole('USER')")
    public BillRespond saveNewBill(HttpServletRequest request){
        
        HttpSession session = request.getSession();
        BillRespond respond = (BillRespond) session.getAttribute("respond");
        // System.out.println("save bill: respond" + respond);
        Bill newBill = (Bill) session.getAttribute("newBill");
        // System.out.println("save bill: newbill" + newBill);
        List<Ticket> listTickets = (List<Ticket>) session.getAttribute("listTicket");
        // System.out.println("save bill:" + respond);
        // System.out.println("save bill:" + newBill);
        // System.out.println("save bill:" + listTickets);

        billRepository.save(newBill);
        listTickets.forEach(ticket -> {
            String scheduleId = ticket.getSchedule().getId();
            String seatId = ticket.getSeat().getSeatName();
            if (!ticketRepository.findByScheduleIdAndSeatId(scheduleId, seatId).isEmpty()) {
                throw new RuntimeException("Seat had been reserved!");
            }
            ticket.setBill(newBill);
            ticketRepository.save(ticket);
        });
        respond.setStatus("paid");
        return respond ;
    }

    public List<BillRespond> findMyBill()
    {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        List<Bill> listBill = new ArrayList<Bill>();
        listBill = billRepository.findByUserId(user.getId());
        List<BillRespond> listRespond = new ArrayList<BillRespond>();
        for(Bill bill : listBill){
            List<Ticket> listTickets = ticketRepository.findByBillId(bill.getId());
            BillRespond respond = new BillRespond();

            Schedule schedule = scheduleRepository.findById(listTickets.get(0).getSchedule().getId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found "));
            respond.setCreatedTime(bill.getCreatedTime());

            respond.setUserName(user.getUserName());

            Movie movie = movieRepository.findById(schedule.getMovie().getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            respond.setMovieName(movie.getMovieName());

            respond.setDate(schedule.getDate());

            respond.setShowTime(schedule.getShowTime());

            Room room = roomRepository.findById(schedule.getRoom().getId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));
            respond.setRoomName(room.getRoomName());

            List<String> listSeatName = new ArrayList<String>();
            for(Ticket ticket : listTickets){
                Seat seat = seatRepository.findById(ticket.getSeat().getId()).orElseThrow(() -> new RuntimeException("Seat not found"));
                listSeatName.add(seat.getSeatName());
            }
            respond.setListSeatName(listSeatName);

            respond.setTotalPrice(bill.getTotalPrice());

            respond.setStatus("Paid");

        }
        return listRespond;
    }
    // @PreAuthorize("hasRole('ADMIN')")
    public List<Bill> findAllBill() {
        return billRepository.findAll();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public List<Bill> findAllBillByUserId(String id) {
        return billRepository.findByUserId(id);
    }
}
