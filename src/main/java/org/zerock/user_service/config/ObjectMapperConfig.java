package org.zerock.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ObjectMapperConfig {
  
 @Bean
 public ObjectMapper objectMapper() {
   ObjectMapper mapper = new ObjectMapper();
   mapper.registerModule(new JavaTimeModule());
   // LocalDateTime 파싱 지원
   mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
   // 없는 필드는 무시
   return mapper;
 }
}
