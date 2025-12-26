package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.ForbiddenException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public RetweetService(
            RetweetRepository retweetRepository,
            UserRepository userRepository,
            TweetRepository tweetRepository
    ) {
        this.retweetRepository = retweetRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public Retweet retweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found."));

        boolean alreadyRetweeted = retweetRepository
                .findByUser_IdAndTweet_Id(userId, tweetId)
                .isPresent();
        if (alreadyRetweeted) {
            throw new RuntimeException("Retweet already exists.");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        return retweetRepository.save(retweet);
    }

    public void deleteRetweet(Long userId, Long tweetId) {
        Retweet retweet = retweetRepository.findByUser_IdAndTweet_Id(userId, tweetId)
                .orElseThrow(() -> new NotFoundException("Retweet not found."));
        retweetRepository.delete(retweet);
    }

    public void deleteRetweetById(Long retweetId, Long userId) {
        Retweet retweet = retweetRepository.findById(retweetId)
                .orElseThrow(() -> new NotFoundException("Retweet not found."));

        if (!retweet.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User is not allowed to delete this retweet.");
        }

        retweetRepository.delete(retweet);
    }
}
