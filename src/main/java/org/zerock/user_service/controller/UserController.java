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
     return new String[]{"Hello", "User",  "Service", "Updated"};
 }
}

