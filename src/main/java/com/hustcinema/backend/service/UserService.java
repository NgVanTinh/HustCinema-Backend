package com.hustcinema.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.hustcinema.backend.dto.request.UserChangePasswordRequest;
import com.hustcinema.backend.dto.request.UserCreationRequest;
import com.hustcinema.backend.dto.request.UserUpdateRequest;
import com.hustcinema.backend.dto.respond.UserRespond;
import com.hustcinema.backend.enums.Role;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    // @PreAuthorize("!hasRole('ADMIN') and !hasRole('USER')")
    public User createNewUser(UserCreationRequest request) {
        // System.out.println(request.getGender());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");            
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Số điện thoại đã tồn tại!");
        }
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        newUser.setAge(request.getAge());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setGender(request.getGender());
        newUser.setUserName(request.getUserName());
        String role = Role.USER.name();
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public List<UserRespond> allUser() {
        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();
        List<UserRespond> usersRespond = new ArrayList<UserRespond>();
        for (User user : users) {
            UserRespond userRespond = new UserRespond();
            userRespond.setAge(user.getAge());
            userRespond.setEmail(user.getEmail());
            userRespond.setPhoneNumber(user.getPhoneNumber());
            userRespond.setFirstName(user.getFirstName());
            userRespond.setId(user.getId());
            userRespond.setLastName(user.getLastName());
            userRespond.setGender(user.getGender());
            userRespond.setUserName(user.getUserName());
            usersRespond.add(userRespond);
        }
        return usersRespond;
    }

    public UserRespond getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));

        UserRespond userRespond = new UserRespond();
        userRespond.setAge(user.getAge());
        userRespond.setEmail(user.getEmail());
        userRespond.setPhoneNumber(user.getPhoneNumber());
        userRespond.setFirstName(user.getFirstName());
        userRespond.setId(user.getId());
        userRespond.setLastName(user.getLastName());
        userRespond.setGender(user.getGender());
        userRespond.setUserName(user.getUserName());
        return userRespond;
        
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public User updateUser(UserUpdateRequest newUser) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        User existUsernameUser = userRepository.findByUserName(newUser.getUserName()).orElse(user);
        User existEmailUser = userRepository.findByEmail(newUser.getEmail()).orElse(user);
        User existPhoneNumberUser = userRepository.findByPhoneNumber(newUser.getPhoneNumber()).orElse(user);
        
        if(!existUsernameUser.getUserName().equals(user.getUserName()))
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        else if (!existEmailUser.getEmail().equals(user.getEmail()))
            throw new RuntimeException("Email đã tồn tại!");
        else if (!existPhoneNumberUser.getPhoneNumber().equals(user.getPhoneNumber()))
            throw new RuntimeException("Số điện thoại đã tồn tại!");
        else {
            user.setAge(newUser.getAge());
            user.setEmail(newUser.getEmail());
            user.setPhoneNumber(newUser.getPhoneNumber());
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName()); 
            user.setGender(newUser.getGender());
            user.setUserName(newUser.getUserName());

            return userRepository.save(user);
        }  
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public User changePasswordUser(UserChangePasswordRequest newUser) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUserName(name).orElseThrow(() -> new RuntimeException("user not found"));
        System.out.println(newUser.getOldPassword() + " " + newUser.getNewPassword());
        System.out.println(user.getPassword());
        System.out.println(passwordEncoder.matches(newUser.getOldPassword(), user.getPassword()));
        if (!passwordEncoder.matches(newUser.getOldPassword(), user.getPassword())) {
            // Xử lý mật khẩu cũ không khớp
            throw new RuntimeException("Mật khẩu hiện tại không đúng!");
        } else {
            // Xử lý thay đổi mật khẩu
            user.setPassword( passwordEncoder.encode(newUser.getNewPassword()));
            return userRepository.save(user);
        }

    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with id = " + id + " not found!");
        }
        userRepository.deleteById(id);
        return "User with id = " + id + " has been deleted successfully";
    }
    
}
