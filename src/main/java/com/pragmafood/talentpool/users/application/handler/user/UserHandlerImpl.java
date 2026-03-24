package com.pragmafood.talentpool.users.application.handler.user;

import com.pragmafood.talentpool.users.application.dto.request.CreateClientRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateEmployeeRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateOwnerRequest;
import com.pragmafood.talentpool.users.application.dto.response.UserResponse;
import com.pragmafood.talentpool.users.application.mapper.UserDtoMapper;
import com.pragmafood.talentpool.users.domain.api.UserServicePort;
import com.pragmafood.talentpool.users.domain.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserServicePort userServicePort;
    private final UserDtoMapper mapper;


    @Transactional
    @Override
    public UserResponse createOwner(CreateOwnerRequest request) {
        User user = mapper.requestToModel(request);
        User savedUser = userServicePort.createOwner(user);
        return mapper.toResponse(savedUser);
    }

    @Transactional
    @Override
    public UserResponse createEmployee(CreateEmployeeRequest request) {
        User user = mapper.requestToModel(request);
        User savedUser = userServicePort.createEmployee(user);
        return mapper.toResponse(savedUser);
    }

    @Transactional
    @Override
    public UserResponse createClient(CreateClientRequest request) {
        User user = mapper.requestToModel(request);
        User savedUser = userServicePort.createClient(user);
        return mapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userServicePort.getUserById(id);
        return mapper.toResponse(user);
    }
}
