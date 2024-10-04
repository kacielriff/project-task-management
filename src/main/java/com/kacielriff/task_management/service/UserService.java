package com.kacielriff.task_management.service;

import com.kacielriff.task_management.dto.shared.MessageDTO;
import com.kacielriff.task_management.dto.user.*;
import com.kacielriff.task_management.entity.User;
import com.kacielriff.task_management.exception.ConflictException;
import com.kacielriff.task_management.exception.NotFoundException;
import com.kacielriff.task_management.exception.UnauthorizedException;
import com.kacielriff.task_management.repository.UserRepository;
import com.kacielriff.task_management.security.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseUserDTO login(LoginUserDTO loginUserDTO) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            User user = (User) authentication.getPrincipal();

            String token = tokenService.generateToken(user);

            return new LoginResponseUserDTO(user.getId(), user.getEmail(), token);
        } catch (BadCredentialsException | AuthenticationCredentialsNotFoundException e) {
            throw new UnauthorizedException("Usuário ou senha inválido(s)");
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException("Usuário não encontrado");
        } catch (Exception e) {
            throw new Exception("Erro interno do servidor");
        }
    }

    public LoginResponseUserDTO create(CreateUserDTO createUserDTO) throws Exception {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new ConflictException("Email já cadastrado");
        }

        User user = new User();

        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setImage(createUserDTO.getImage() == null ? null : createUserDTO.getImage());

        userRepository.save(user);

        return login(new LoginUserDTO(createUserDTO.getEmail(), createUserDTO.getPassword()));
    }

    public MessageDTO forgotPassword(ForgotPasswordDTO forgotPasswordDTO) throws Exception {
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        String token = tokenService.generateRecoverPasswordToken(user);

//        try {
//            emailService.sendEmail();
//        } catch (Exception e) {
//
//        }

        return new MessageDTO("Email para recuperação de senha encaminhado com sucesso.");
    }

    public MessageDTO recoverPassword(RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        Claims claims = tokenService.decryptToken(recoverPasswordDTO.getToken());

        Long userId;

        try {
            userId = Long.valueOf(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new Exception("Formato do ID inválido.");
        }

        String email = claims.get("email", String.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (email == null || !userRepository.existsByEmail(email))
            throw new NotFoundException("Usuário não encontrado.");

        user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getPassword()));
        userRepository.save(user);

        return new MessageDTO("Senha alterada com sucesso.");
    }

    public LoginResponseUserDTO changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        User user = userRepository.findById(getIdLoggedUser())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new LoginResponseUserDTO(user.getId(), user.getEmail(), token);
    }

    public LoggedUserDTO getLoggedUser() throws Exception {
        User user = userRepository.findById(getIdLoggedUser())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        return new LoggedUserDTO(user.getId(), user.getEmail());
    }

    public Long getIdLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) throw new RuntimeException("Acesso não autorizado");

        return Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
