package com.workintech.twitter.controller;

import com.workintech.twitter.dto.requests.TweetRequest;
import com.workintech.twitter.dto.responses.TweetResponse;
import com.workintech.twitter.mapper.TweetMapper;
import com.workintech.twitter.service.TweetService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
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
    public TweetResponse createTweet(@RequestBody @Valid TweetRequest request) {
        return TweetMapper.toResponse(
                tweetService.createTweet(request.getUserId(), request.getContent())
        );
    }

    @GetMapping("/findByUserId")
    public List<TweetResponse> findByUserId(@RequestParam Long userId) {
        return tweetService.findByUserId(userId).stream()
                .map(TweetMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/findById")
    public TweetResponse findById(@RequestParam Long id) {
        return TweetMapper.toResponse(tweetService.findById(id));
    }

    @PutMapping("/{id}")
    public TweetResponse updateTweet(
            @PathVariable("id") Long tweetId,
            @RequestParam Long userId,
            @RequestBody @Valid TweetRequest request
    ) {
        return TweetMapper.toResponse(
                tweetService.updateTweet(tweetId, userId, request.getContent())
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable("id") Long tweetId, @RequestParam Long userId) {
        tweetService.deleteTweet(tweetId, userId);
    }
}
