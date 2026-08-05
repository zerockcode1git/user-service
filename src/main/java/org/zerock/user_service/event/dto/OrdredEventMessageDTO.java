package org.zerock.user_service.event.dto;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdredEventMessageDTO {

 @JsonProperty("ono")
 private String orderId;

 private String userId;

 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 @JsonProperty("orderedTime")
 private LocalDateTime orderedTime;
}
