package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.ForbiddenException;
import com.workintech.twitter.exception.NotFoundException;
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
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TweetService tweetService;

    @Test
    void createTweet_savesTweetForExistingUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.save(any(Tweet.class))).thenAnswer(inv -> inv.getArgument(0));

        Tweet tweet = tweetService.createTweet(1L, "hello");

        Assertions.assertEquals("hello", tweet.getContent());
        Assertions.assertEquals(1L, tweet.getUser().getId());
    }

    @Test
    void findById_throwsWhenNotFound() {
        when(tweetRepository.findById(99L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> tweetService.findById(99L));
    }

    @Test
    void updateTweet_throwsForbiddenWhenNotOwner() {
        User owner = new User();
        owner.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setUser(owner);

        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        Assertions.assertThrows(ForbiddenException.class,
                () -> tweetService.updateTweet(10L, 2L, "new"));
    }

    @Test
    void updateTweet_updatesContentWhenOwner() {
        User owner = new User();
        owner.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setUser(owner);
        tweet.setContent("old");

        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));
        when(tweetRepository.save(any(Tweet.class))).thenAnswer(inv -> inv.getArgument(0));

        Tweet updated = tweetService.updateTweet(10L, 1L, "new");

        Assertions.assertEquals("new", updated.getContent());
    }

    @Test
    void deleteTweet_deletesWhenOwner() {
        User owner = new User();
        owner.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setUser(owner);

        when(tweetRepository.findById(10L)).thenReturn(Optional.of(tweet));

        tweetService.deleteTweet(10L, 1L);

        verify(tweetRepository).delete(tweet);
    }
}
