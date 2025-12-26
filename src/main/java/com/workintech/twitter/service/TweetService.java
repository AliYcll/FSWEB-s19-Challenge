package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.ForbiddenException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public Tweet createTweet(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));

        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(content);
        return tweetRepository.save(tweet);
    }

    public Tweet findById(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found."));
    }

    public List<Tweet> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        return tweetRepository.findByUser_Id(user.getId());
    }

    public Tweet updateTweet(Long tweetId, Long userId, String content) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found."));

        if (!tweet.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User is not allowed to update this tweet.");
        }

        tweet.setContent(content);
        return tweetRepository.save(tweet);
    }

    public void deleteTweet(Long tweetId, Long userId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found."));

        if (!tweet.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User is not allowed to delete this tweet.");
        }

        tweetRepository.delete(tweet);
    }
}
