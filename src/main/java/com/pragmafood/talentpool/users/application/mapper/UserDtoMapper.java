package com.pragmafood.talentpool.users.application.mapper;

import com.pragmafood.talentpool.users.application.dto.request.CreateEmployeeRequest;
import com.pragmafood.talentpool.users.application.dto.request.CreateOwnerRequest;
import com.pragmafood.talentpool.users.application.dto.response.UserResponse;
import com.pragmafood.talentpool.users.domain.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserDtoMapper {

    User requestToModel(CreateOwnerRequest request);
    User requestToModel(CreateEmployeeRequest request);
    UserResponse toResponse(User user);

}
