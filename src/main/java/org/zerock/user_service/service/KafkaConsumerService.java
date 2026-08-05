package org.zerock.user_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.zerock.user_service.event.dto.OrdredEventMessageDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final ObjectMapper objectMapper;

   @KafkaListener(
       topics = "order.created",
       groupId = "product-group-3")
  public void consumeOrderCreated(
         @Payload String event,
         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
         @Header(KafkaHeaders.OFFSET) long offset) {

   log.info("User Service Kafka Consumer");      
   log.info("Received message: {}", event);

   try{

     OrdredEventMessageDTO orderEvent =
     objectMapper.readValue(event, OrdredEventMessageDTO.class);

     log.info(orderEvent);

   }catch(Exception e){
     log.error("Error processing message: {}", e.getMessage());
     e.printStackTrace();
   }

   log.info("수신: {} | partition={}, offset={}", event, partition, offset);
  }



 @KafkaListener(
         topics = "sample.created",
         groupId = "product-group-3")
 public void consume(
         @Payload String event,
         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
         @Header(KafkaHeaders.OFFSET) long offset) {


   log.info("User Service Kafka Consumer");      
   log.info("Received message: {}", event);
   log.info("수신: {} | partition={}, offset={}", event, partition, offset);
 }
}
