package com.freelance.services.impl;

import com.freelance.dto.DtoUser;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.jwt.JWTService;
import com.freelance.jwt.RefreshTokenRequest;
import com.freelance.model.AuthRequest;
import com.freelance.model.AuthResponse;
import com.freelance.model.RefreshToken;
import com.freelance.model.User;
import com.freelance.repository.RefreshTokenRepository;
import com.freelance.repository.UserRepository;
import com.freelance.services.CommonService;
import com.freelance.services.IAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTService jwtService;

    private User createUser(AuthRequest input){
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setMail(input.getMail());
        user.setRole(input.getRole());
        user.setCreateDate(new Date());
        return user;
    }

    @Override
    public DtoUser register(AuthRequest input) {
        try {
            DtoUser dtoUser = new DtoUser();
            if (userRepository.findByUsername(input.getUsername()).isPresent()) {
                throw new BaseException(new ErrorMessage(MessageType.USER_ALREADY_EXISTS, "username: " + input.getUsername()));
            }

            if (userRepository.findByMail(input.getMail()).isPresent()) {
                throw new BaseException(new ErrorMessage(MessageType.USER_ALREADY_EXISTS, "mail: " + input.getMail()));
            }

            User savedUser = userRepository.save(createUser(input));
            BeanUtils.copyProperties(savedUser, dtoUser);
            return dtoUser;
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.REGISTER_FAILED, e.getMessage()));
        }
    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken =new RefreshToken();
        refreshToken.setCreateDate(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {
        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
            authenticationProvider.authenticate(authenticationToken);
            Optional<User> optUser = userRepository.findByUsername(input.getUsername());

            String accessToken = jwtService.generateToken(optUser.get());

            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));

            return new AuthResponse(input.getUsername(),accessToken, savedRefreshToken.getRefreshToken());
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.AUTHENTICATION_FAILED, e.getMessage()));
        }
    }

    @Override
    @Transactional
    public void logout() {
        try {
            String username = commonService.getCurrentUsername();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                    ));

            refreshTokenRepository.deleteAllByUser(user);
            SecurityContextHolder.clearContext();

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.LOGOUT_EXCEPTION, e.getMessage()));
        }
    }

    @Override
    public DtoUser getProfile() {
        DtoUser dtoUser = new DtoUser();

        String username = commonService.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                ));

        BeanUtils.copyProperties(user, dtoUser);

        return dtoUser;
    }

    @Override
    @Transactional
    public void deleteProfile() {
        String username = commonService.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                ));

        refreshTokenRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }
}
