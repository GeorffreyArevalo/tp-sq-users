package com.pragmafood.talentpool.users.application.handler.user;

import com.pragmafood.talentpool.users.application.dto.request.CreateClientRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateEmployeeRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateOwnerRequest;
import com.pragmafood.talentpool.users.application.dto.response.UserResponse;

public interface UserHandler {

    UserResponse createOwner(CreateOwnerRequest request);

    UserResponse createEmployee(CreateEmployeeRequest request);

    UserResponse createClient(CreateClientRequest request);

    UserResponse getUserById(Long id);
}
