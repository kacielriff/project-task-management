package com.kacielriff.task_management.controller;

import com.kacielriff.task_management.docs.AuthControllerDocs;
import com.kacielriff.task_management.dto.shared.MessageDTO;
import com.kacielriff.task_management.dto.user.*;
import com.kacielriff.task_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseUserDTO> login(@RequestBody LoginUserDTO loginUserDTO) throws Exception {
        return new ResponseEntity<>(userService.login(loginUserDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseUserDTO> create(@RequestBody CreateUserDTO createUserDTO) throws Exception {
        return new ResponseEntity<>(userService.create(createUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageDTO> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) throws Exception {
        return new ResponseEntity<>(userService.forgotPassword(forgotPasswordDTO), HttpStatus.OK);
    }

    @PostMapping("/recover-password")
    public ResponseEntity<MessageDTO> recoverPassword(@RequestBody RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        return new ResponseEntity<>(userService.recoverPassword(recoverPasswordDTO), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<LoginResponseUserDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        return new ResponseEntity<>(userService.changePassword(changePasswordDTO), HttpStatus.OK);
    }

    @GetMapping("/logged-user")
    public ResponseEntity<LoggedUserDTO> getLoggedUser() throws Exception {
        return new ResponseEntity<>(userService.getLoggedUser(), HttpStatus.OK);
    }
}
