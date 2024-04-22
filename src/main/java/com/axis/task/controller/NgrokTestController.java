package com.axis.task.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NgrokTestController {
    @GetMapping("/test")
    public String test() {
        return "Hello from Ngrok! Jenkins deploy";
    }
}
