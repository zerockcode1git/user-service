// package org.zerock.user_service.controller;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.log4j.Log4j2;


// @RestController
// @Log4j2
// @RequiredArgsConstructor
// public class UserController {

//   @Value("${app.title}")
//   private String title;
//   @Value("${app.awsTitle}")
//   private String awsTitle;

//   @PostConstruct
//   public void init() {
//     log.info("===================================");
//     log.info("title: " + title);
//     log.info("awsTitle: " + awsTitle);
//     log.info("===================================");
//   }


//   @GetMapping("hello")
//   public String[] hello() {

//      return new String[]{"Hello", "User",  "Service", "Updated"};
//   }


// }


package org.zerock.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {

  @GetMapping("hello")
 public String[] hello() {
     return new String[]{"Hello", "User",  "Service"};
 }
}
