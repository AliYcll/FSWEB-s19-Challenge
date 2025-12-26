package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public Like likeTweet(Long userId, Long tweetId) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found."));


        Optional<Like> foundLike = likeRepository.findByUser_IdAndTweet_Id(userId, tweetId);

        if (foundLike.isPresent()) {
            throw new RuntimeException("Like already exists");
        }

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        return likeRepository.save(like);

    }

    public void dislikeTweet(Long userId, Long tweetId) {

        Like like = likeRepository.findByUser_IdAndTweet_Id(userId, tweetId)
                .orElseThrow(() -> new NotFoundException("Like not found"));

        likeRepository.delete(like);
    }
}
