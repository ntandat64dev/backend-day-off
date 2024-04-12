package com.masterspring.backenddayoff.mapper;

import com.masterspring.backenddayoff.dto.response.AuthResponse;
import com.masterspring.backenddayoff.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthResponseMapper {

    @Mapping(target = "remainDays", expression = "java(user.getLeaveRemain().getRemainDays())")
    AuthResponse fromUser(User user);
}
