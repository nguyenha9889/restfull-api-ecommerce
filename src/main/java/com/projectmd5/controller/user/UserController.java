package com.projectmd5.controller.user;

import com.projectmd5.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api.myservice.com/v1")
public class UserController {

   private final IUserService userService;
}
