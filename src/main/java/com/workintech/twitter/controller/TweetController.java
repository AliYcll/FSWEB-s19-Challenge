package com.workintech.twitter.controller;

import com.workintech.twitter.dto.TweetRequest;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public Tweet createTweet(@RequestBody TweetRequest request) {
        return tweetService.createTweet(request.getUserId(), request.getContent());
    }

    @GetMapping("/findByUserId")
    public List<Tweet> findByUserId(@RequestParam Long userId) {
        return tweetService.findByUserId(userId);
    }

    @GetMapping("/findById")
    public Tweet findById(@RequestParam Long id) {
        return tweetService.findById(id);
    }

    @PutMapping("/{id}")
    public Tweet updateTweet(
            @PathVariable("id") Long tweetId,
            @RequestParam Long userId,
            @RequestBody TweetRequest request
    ) {
        return tweetService.updateTweet(tweetId, userId, request.getContent());
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable("id") Long tweetId, @RequestParam Long userId) {
        tweetService.deleteTweet(tweetId, userId);
    }
}
