package com.pragmafood.talentpool.users.infrastructure.output.jpa.mapper;

import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.infrastructure.output.jpa.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserEntityMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}
