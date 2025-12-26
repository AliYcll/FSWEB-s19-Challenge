package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.ForbiddenException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.CommentRepository;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void createComment_throwsWhenUserMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.createComment(1L, 2L, "hi"));
    }

    @Test
    void updateComment_throwsForbiddenWhenNotOwner() {
        User owner = new User();
        owner.setId(1L);
        Comment comment = new Comment();
        comment.setUser(owner);

        when(commentRepository.findById(5L)).thenReturn(Optional.of(comment));

        Assertions.assertThrows(ForbiddenException.class,
                () -> commentService.updateComment(5L, 2L, "new"));
    }

    @Test
    void createComment_savesWhenUserAndTweetExist() {
        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.findById(2L)).thenReturn(Optional.of(tweet));
        when(commentRepository.save(any(Comment.class))).thenAnswer(inv -> inv.getArgument(0));

        Comment saved = commentService.createComment(1L, 2L, "hello");

        Assertions.assertEquals("hello", saved.getContent());
        Assertions.assertEquals(1L, saved.getUser().getId());
        Assertions.assertEquals(2L, saved.getTweet().getId());
    }

    @Test
    void updateComment_updatesContentWhenOwner() {
        User owner = new User();
        owner.setId(1L);
        Comment comment = new Comment();
        comment.setUser(owner);
        comment.setContent("old");

        when(commentRepository.findById(5L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(inv -> inv.getArgument(0));

        Comment updated = commentService.updateComment(5L, 1L, "new");

        Assertions.assertEquals("new", updated.getContent());
    }

    @Test
    void deleteComment_deletesWhenOwner() {
        User owner = new User();
        owner.setId(1L);
        Comment comment = new Comment();
        comment.setUser(owner);

        when(commentRepository.findById(5L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(5L, 1L);

        verify(commentRepository).delete(comment);
    }
}
