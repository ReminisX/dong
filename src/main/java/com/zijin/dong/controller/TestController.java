package com.zijin.dong.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @PostMapping("/copy")
    public String copySelf(@RequestBody String abc) {

        return abc;
    }

}
