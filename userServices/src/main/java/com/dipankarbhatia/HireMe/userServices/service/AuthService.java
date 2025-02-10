package com.dipankarbhatia.HireMe.userServices.service;

import com.dipankarbhatia.HireMe.userServices.dto.LoginRequestDto;
import com.dipankarbhatia.HireMe.userServices.dto.SignupRequestDto;
import com.dipankarbhatia.HireMe.userServices.dto.UserDto;
import com.dipankarbhatia.HireMe.userServices.entity.User;
import com.dipankarbhatia.HireMe.userServices.exception.BadRequestException;
import com.dipankarbhatia.HireMe.userServices.exception.ResourceNotFoundException;
import com.dipankarbhatia.HireMe.userServices.repository.UserRepository;
import com.dipankarbhatia.HireMe.userServices.utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        log.info("signing up a user with email: " + signupRequestDto.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists){
            throw new BadRequestException("user already exists with this email id");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));

        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email: " + loginRequestDto.getEmail());

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()
                -> new BadRequestException("Incorrect Email/Password"));

        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());

        if(!isPasswordMatch) {
            throw new BadRequestException("Incorrect Email/Password");
        }

        return jwtService.generateAccessToken(user);
    }

}
