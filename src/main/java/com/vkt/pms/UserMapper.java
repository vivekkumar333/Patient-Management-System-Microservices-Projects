package com.vkt.pms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vkt.pms.entity.User;
import com.vkt.pms.enums.Status;
import com.vkt.pms.responseDto.UserResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class UserMapper {
    public static UserResponse userToUserResponseMapper(User user){
        return new UserResponse(
                user.getUid(),
                user.getEmail(),
                user.getRole().name(),
                user.getStatus().name(),
                user.getCreatedTimestamp(),
                user.getUpdatedTimestamp()
        );
    }
}
