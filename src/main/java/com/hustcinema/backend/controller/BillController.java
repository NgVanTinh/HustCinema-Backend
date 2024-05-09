package com.hustcinema.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hustcinema.backend.dto.respond.BillRespond;
import com.hustcinema.backend.model.Bill;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.UserRepository;
import com.hustcinema.backend.service.BillService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/api/bill")

public class BillController {
    
    @Autowired
    private BillService billService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    // @PreAuthorize("hasRole('ADMIN')")
    public List<Bill> getAllBill(){
        return billService.findAllBill();
    }

    @GetMapping("/my-bill")
    public List<BillRespond> getMyBill() {
        return billService.findMyBill();
    }

    @GetMapping("/{id}")
    public List<Bill> getAllBillByUserId(@PathVariable String id){
        return billService.findAllBillByUserId(id);
    }

    @PostMapping("/makeBill")
    public BillRespond makeBill(HttpServletRequest request, @RequestBody List<String> selectedSeatIds) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        User user = userRepository.findByUserName(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found "));
        
        String userId = user.getId();
        

        return billService.makeNewBill(request, userId, selectedSeatIds);
    }

    @GetMapping("/saveBill")
    public BillRespond saveBill(HttpServletRequest request) {
        return billService.saveNewBill(request);
    }
    
    
}
