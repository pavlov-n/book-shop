package com.bookshop.service;

import com.bookshop.dto.user.UserRegistrationRequestDto;
import com.bookshop.dto.user.UserResponseDto;
import com.bookshop.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
