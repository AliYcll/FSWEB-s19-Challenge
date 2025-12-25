package com.workintech.twitter.controller;

import com.workintech.twitter.dto.RetweetRequest;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.service.RetweetService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final RetweetService retweetService;

    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public Retweet retweet(@RequestBody RetweetRequest request) {
        return retweetService.retweet(request.getUserId(), request.getTweetId());
    }

    @DeleteMapping("/{id}")
    public void deleteRetweet(@PathVariable("id") Long retweetId, @RequestParam Long userId) {
        retweetService.deleteRetweetById(retweetId, userId);
    }
}
