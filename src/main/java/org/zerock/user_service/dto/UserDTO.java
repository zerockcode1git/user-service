package org.zerock.user_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

 private String userId;
 private String userPw;
 private String username;
 private String rolename;
 private LocalDateTime regDate;
 private LocalDateTime updateDate;
}
