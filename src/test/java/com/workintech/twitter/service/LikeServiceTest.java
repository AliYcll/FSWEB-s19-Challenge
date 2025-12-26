package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void likeTweet_throwsWhenAlreadyExists() {
        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(2L)).thenReturn(Optional.of(tweet));
        when(likeRepository.findByUser_IdAndTweet_Id(1L, 2L))
                .thenReturn(Optional.of(new Like()));

        Assertions.assertThrows(RuntimeException.class,
                () -> likeService.likeTweet(1L, 2L));
    }

    @Test
    void dislikeTweet_throwsWhenLikeMissing() {
        when(likeRepository.findByUser_IdAndTweet_Id(1L, 2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> likeService.dislikeTweet(1L, 2L));
    }

    @Test
    void likeTweet_savesWhenNotExists() {
        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(2L)).thenReturn(Optional.of(tweet));
        when(likeRepository.findByUser_IdAndTweet_Id(1L, 2L))
                .thenReturn(Optional.empty());
        when(likeRepository.save(any(Like.class))).thenAnswer(inv -> inv.getArgument(0));

        Like saved = likeService.likeTweet(1L, 2L);

        Assertions.assertEquals(1L, saved.getUser().getId());
        Assertions.assertEquals(2L, saved.getTweet().getId());
    }
}
