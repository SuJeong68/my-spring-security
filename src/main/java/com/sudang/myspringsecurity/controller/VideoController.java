package com.sudang.myspringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @GetMapping("/video/{country}/{language}")
    public String videoMap(@PathVariable String country,
                           @PathVariable String language) {
        return "Video allowed for " + country + " " + language;
    }
}
