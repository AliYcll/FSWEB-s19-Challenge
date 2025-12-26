package com.workintech.twitter.controller;

import com.workintech.twitter.dto.requests.RetweetRequest;
import com.workintech.twitter.dto.responses.RetweetResponse;
import com.workintech.twitter.mapper.RetweetMapper;
import com.workintech.twitter.service.RetweetService;
import jakarta.validation.Valid;
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
    public RetweetResponse retweet(@RequestBody @Valid RetweetRequest request) {
        return RetweetMapper.toResponse(
                retweetService.retweet(request.getUserId(), request.getTweetId())
        );
    }

    @DeleteMapping("/{id}")
    public void deleteRetweet(@PathVariable("id") Long retweetId, @RequestParam Long userId) {
        retweetService.deleteRetweetById(retweetId, userId);
    }
}
